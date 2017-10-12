package agency.tango.viking.processor.module;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

import javax.lang.model.element.Modifier;

import agency.tango.viking.processor.AnnotatedClass;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

public class ViewModelMappingsBuilder {

  public TypeSpec buildTypeSpec(List<AnnotatedClass> annotatedClasses) {

    AnnotationSpec.Builder moduleAnnotationBuilder = AnnotationSpec.builder(Module.class);

    TypeSpec.Builder builder = TypeSpec.classBuilder("ViewModelMappings")
        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
        .addAnnotation(moduleAnnotationBuilder.build());

    for (AnnotatedClass annotatedClass : annotatedClasses) {

      builder.addMethod(MethodSpec.methodBuilder(
          String.format("provide%s", annotatedClass.getClassName()))
          .addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC)
          .addAnnotation(Binds.class)
          .addAnnotation(IntoMap.class)
          .addAnnotation(AnnotationSpec.builder(
              ClassName.get("agency.tango.viking.example.di", "ViewModelKey"))
              .addMember("value", "$T.class",
                  ClassName.get(annotatedClass.getPackage(), annotatedClass.getClassName()))
              .build())
          .addParameter(ClassName.get(annotatedClass.getPackage(), annotatedClass.getClassName()),
              "viewModel")
          .returns(ClassName.get("android.arch.lifecycle", "ViewModel"))
          .build());
    }

    return builder.build();
  }
}
