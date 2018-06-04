package agency.tango.viking.processor.module;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import javax.lang.model.element.ExecutableElement;
import agency.tango.viking.processor.AnnotatedClass;
import agency.tango.viking.processor.ExtendedCodeBuilder;
import dagger.Module;
import dagger.Provides;

import static com.squareup.javapoet.ClassName.get;
import static com.squareup.javapoet.MethodSpec.methodBuilder;
import static com.squareup.javapoet.TypeSpec.classBuilder;
import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PUBLIC;

public class ModuleScopeCodeGenerator implements ExtendedCodeBuilder {
  private String moduleName;

  public ModuleScopeCodeGenerator(String moduleName) {
    this.moduleName = moduleName;
  }

  @Override
  public TypeSpec buildTypeSpec(AnnotatedClass... annotatedClasses) {
    String module = moduleName + "Fragments_Module";
    TypeSpec.Builder builder = classBuilder(module).addModifiers(PUBLIC, ABSTRACT);

    builder.addAnnotation(Module.class);

    for (AnnotatedClass annotatedClass : annotatedClasses) {
      builder.addMethod(
          methodBuilder("provides" + annotatedClass.getClassName())
              .addAnnotation(AnnotationSpec.builder(get("dagger.android", "ContributesAndroidInjector")).build())
              .addModifiers(PUBLIC, ABSTRACT)
              .returns(get(annotatedClass.getTypeElement()))
              .build());

      for (int i = 0; i < annotatedClass.getExecutableElements().size(); i++) {
        ExecutableElement element = (ExecutableElement) annotatedClass.getExecutableElements().get(i);

        builder.addMethod(methodBuilder("provides" + element.getSimpleName())
            .addAnnotation(Provides.class)
            .addModifiers(PUBLIC)
            .addParameter(ParameterSpec.builder(get(annotatedClass.getTypeElement()), "view").build())
            .addCode("return view.$N();", element.getSimpleName())
            .returns(TypeName.get(element.getReturnType()))
            .build());
      }
    }

    return builder.build();
  }
}
