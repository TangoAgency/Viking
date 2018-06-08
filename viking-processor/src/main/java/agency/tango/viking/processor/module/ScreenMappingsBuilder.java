package agency.tango.viking.processor.module;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import net.droidlabs.dagger.annotations.ActivityScope;
import java.util.List;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import agency.tango.viking.processor.AnnotatedClass;
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
      AnnotationSpec.Builder annotationBuilder =
          AnnotationSpec.builder(get("dagger.android", "ContributesAndroidInjector"));

      String modules = ModuleBuilderUtil.buildModulesAttribute(annotatedClass);
      StringBuilder modulesBuilder = new StringBuilder(modules).deleteCharAt(modules.length() - 1);

      for (TypeMirror type : typesWithScope) {
        if (annotatedClass.getTypeMirror().equals(type)) {
          modulesBuilder
              .append(", ")
              .append(TypeName.get(type).toString())
              .append("Fragments_Module.class");
        }
      }

      modules = modulesBuilder.append("}").toString();
      annotationBuilder.addMember("modules", modules);

      builder.addMethod(MethodSpec.methodBuilder(
          String.format("provide%s", annotatedClass.getClassName()))
          .addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC)
          .addAnnotation(
              annotationBuilder.build()
          )
          .addAnnotation(AnnotationSpec.builder(get(ActivityScope.class)).build())
          .returns(get(annotatedClass.getPackage(), annotatedClass.getClassName()))
          .build());
    }

    return builder.build();
  }
}
