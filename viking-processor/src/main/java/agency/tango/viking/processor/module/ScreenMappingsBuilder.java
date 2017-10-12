package agency.tango.viking.processor.module;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

import javax.lang.model.element.Modifier;

import agency.tango.viking.processor.AnnotatedClass;
import dagger.Module;

public class ScreenMappingsBuilder {

  public TypeSpec buildTypeSpec(List<AnnotatedClass> annotatedClasses) {

    AnnotationSpec.Builder moduleAnnotationBuilder = AnnotationSpec.builder(Module.class);

    TypeSpec.Builder builder = TypeSpec.classBuilder("ScreenMappings")
        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
        .addAnnotation(moduleAnnotationBuilder.build());

    for (AnnotatedClass annotatedClass : annotatedClasses) {

      builder.addMethod(MethodSpec.methodBuilder(
          String.format("provide%s", annotatedClass.getClassName()))
          .addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC)
          .addAnnotation(ClassName.get("dagger.android", "ContributesAndroidInjector"))
          .returns(ClassName.get(annotatedClass.getPackage(), annotatedClass.getClassName()))
          .build());
    }

    return builder.build();
  }
}
