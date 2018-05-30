package agency.tango.viking.processor.module;

import com.google.common.base.Joiner;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import net.droidlabs.dagger.annotations.ActivityScope;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.lang.model.type.TypeMirror;
import agency.tango.viking.annotations.AutoModule;
import agency.tango.viking.di.ScreenComponent;
import agency.tango.viking.di.ScreenComponentBuilder;
import agency.tango.viking.processor.AnnotatedClass;
import agency.tango.viking.processor.CodeBuilder;
import dagger.Subcomponent;

import static agency.tango.viking.processor.Util.getAnnotation;
import static com.squareup.javapoet.ClassName.get;
import static com.squareup.javapoet.TypeSpec.interfaceBuilder;
import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

public class ComponentCodeBuilder implements CodeBuilder {
  @Override
  public TypeSpec buildTypeSpec(AnnotatedClass annotatedClass) {
    String module = annotatedClass.getClassName() + "_Module.class";
    TypeSpec.Builder builder = interfaceBuilder(
        annotatedClass.getClassName() + "_Component")
        .addModifiers(PUBLIC)
        .addSuperinterface(ParameterizedTypeName.get(get(ScreenComponent.class),
            get(annotatedClass.getTypeElement())));

    Map<String, Object> parsedAnnotation = getAnnotation(AutoModule.class,
        annotatedClass.getTypeElement());

    Object[] typeMirrors = (Object[]) parsedAnnotation.get("includes");

    List<String> types = new ArrayList<>();

    for (Object obj : typeMirrors) {
      TypeMirror typeMirror = (TypeMirror) obj;
      types.add(TypeName.get(typeMirror).toString() + ".class");
    }

    builder.addAnnotation(
        AnnotationSpec.builder(get(ActivityScope.class)).build());

    builder.addAnnotation(AnnotationSpec.builder(get(Subcomponent.class))
        .addMember("modules", String.format("{%s,%s}", module, Joiner.on(",").join(types)))
        .build());

    TypeSpec.Builder builderBuilder = interfaceBuilder("Builder")
        .addModifiers(PUBLIC, STATIC)
        .addAnnotation(Subcomponent.Builder.class)
        .addSuperinterface(
            ParameterizedTypeName.get(get(ScreenComponentBuilder.class),
                get(annotatedClass.getPackage(),
                    annotatedClass.getClassName() + "_Module"),
                get(annotatedClass.getPackage(),
                    annotatedClass.getClassName() + "_Component")));

    for (Object obj : typeMirrors) {
      TypeMirror typeMirror = (TypeMirror) obj;

      builderBuilder.addMethod(MethodSpec.methodBuilder("screenModule")
          .addModifiers(PUBLIC, ABSTRACT)
          .addParameter(TypeName.get(typeMirror), "module", FINAL)
          .returns(get(annotatedClass.getPackage(),
              annotatedClass.getClassName() + "_Component", "Builder"))
          .build());
    }

    builder.addType(builderBuilder.build());

    return builder.build();
  }
}
