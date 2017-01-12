package agency.tango.viking.processor.module;

import android.support.annotation.NonNull;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

import javax.lang.model.element.Modifier;

import agency.tango.viking.annotations.ActivityComponentBuilder;
import agency.tango.viking.annotations.ActivityKey;
import agency.tango.viking.processor.AnnotatedClass;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

public class ScreenBindingsModuleBuilder {

  public TypeSpec buildTypeSpec(List<AnnotatedClass> annotatedClasses) {

    AnnotationSpec.Builder moduleAnnotationBuilder = AnnotationSpec.builder(Module.class);

    for (AnnotatedClass annotatedClassClass : annotatedClasses) {

      moduleAnnotationBuilder.addMember("subcomponents", "$T.class",
          getComponentClassName(annotatedClassClass));
    }

    TypeSpec.Builder builder = TypeSpec.classBuilder("ScreenBindingsModule")
        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
        .addAnnotation(moduleAnnotationBuilder.build());

    for (AnnotatedClass annotatedClass : annotatedClasses) {

      builder.addMethod(MethodSpec.methodBuilder(
          String.format("provide%s%s", annotatedClass.getClassName(), "Component"))
          .addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC)
          .addAnnotation(Binds.class)
          .addAnnotation(IntoMap.class)
          .addAnnotation(AnnotationSpec.builder(ClassName.get(ActivityKey.class))
              .addMember("value", "$T.class", ClassName.get(annotatedClass.getPackage(),
                  annotatedClass.getClassName()))
              .build())
          .returns(ClassName.get(ActivityComponentBuilder.class))
          .addParameter(ParameterSpec.builder(
              getBuilderClassName(annotatedClass), "builder").build())
          .build());
    }

    return builder.build();
  }

  @NonNull
  private ClassName getBuilderClassName(AnnotatedClass annotatedClass) {

    return ClassName.get(annotatedClass.getPackage(),
        annotatedClass.getClassName() + "_Component", "Builder");
  }

  @NonNull
  private ClassName getComponentClassName(AnnotatedClass annotatedClass) {
    return ClassName.get(annotatedClass.getPackage(),
        annotatedClass.getClassName() + "_Component");
  }
}
