package agency.tango.viking.processor.module;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import net.droidlabs.dagger.annotations.ActivityScope;
import java.util.List;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import agency.tango.viking.processor.AnnotatedClass;
import agency.tango.viking.processor.Util;
import agency.tango.viking.processor.annotation.AnnotationAttributesBuilder;
import agency.tango.viking.processor.annotation.AnnotationUtil;
import dagger.Module;

import static com.squareup.javapoet.ClassName.get;

public class ScreenMappingsBuilder {

  public TypeSpec buildTypeSpec(List<AnnotatedClass> annotatedClasses,
      List<TypeMirror> typesWithScope) {

    AnnotationSpec.Builder moduleAnnotationBuilder = AnnotationSpec.builder(Module.class);

    TypeSpec.Builder builder = TypeSpec.classBuilder("ScreenMappings")
        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
        .addAnnotation(moduleAnnotationBuilder.build());

    for (AnnotatedClass annotatedClass : annotatedClasses) {
      builder.addMethod(buildProvideMethod(annotatedClass, typesWithScope));
    }

    return builder.build();
  }

  private MethodSpec buildProvideMethod(AnnotatedClass annotatedClass,
      List<TypeMirror> typesWithScope) {
    return MethodSpec.methodBuilder(
        String.format("provide%s", annotatedClass.getClassName()))
        .addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC)
        .addAnnotation(buildDaggerAnnotation(annotatedClass, typesWithScope))
        .addAnnotation(AnnotationSpec.builder(get(ActivityScope.class)).build())
        .returns(get(annotatedClass.getPackage(), annotatedClass.getClassName()))
        .build();
  }

  private AnnotationSpec buildDaggerAnnotation(AnnotatedClass annotatedClass,
      List<TypeMirror> typesWithScope) {
    AnnotationSpec.Builder annotationBuilder =
        AnnotationSpec.builder(get("dagger.android", "ContributesAndroidInjector"));

    AnnotationAttributesBuilder modulesAttributesBuilder = new AnnotationAttributesBuilder()
        .addAttribute(annotatedClass.getPackage(), annotatedClass.getClassName() + "_Module")
        .addAttributes(AnnotationUtil.getValuesForAttribute("includes", annotatedClass));

    for (TypeMirror type : typesWithScope) {
      if (annotatedClass.getTypeMirror().equals(type)) {
        modulesAttributesBuilder
            .addAttribute(
                Util.getPackageName(type), Util.getSimpleTypeName(type) + "Fragments_Module");
      }
    }

    annotationBuilder.addMember("modules", modulesAttributesBuilder.build());

    return annotationBuilder.build();
  }
}
