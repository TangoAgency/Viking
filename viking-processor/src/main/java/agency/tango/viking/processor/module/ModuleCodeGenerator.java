package agency.tango.viking.processor.module;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import javax.annotation.processing.ProcessingEnvironment;
import javax.inject.Named;
import javax.lang.model.element.ExecutableElement;
import agency.tango.viking.annotations.AutoProvides;
import agency.tango.viking.processor.AnnotatedClass;
import agency.tango.viking.processor.CodeBuilder;
import dagger.Module;
import dagger.Provides;

import static com.squareup.javapoet.ClassName.get;
import static com.squareup.javapoet.MethodSpec.methodBuilder;
import static com.squareup.javapoet.TypeSpec.classBuilder;
import static javax.lang.model.element.Modifier.PUBLIC;

public class ModuleCodeGenerator implements CodeBuilder {
  private final ProcessingEnvironment processingEnvironment;

  public ModuleCodeGenerator(ProcessingEnvironment processingEnvironment) {
    this.processingEnvironment = processingEnvironment;
  }

  @Override
  public TypeSpec buildTypeSpec(AnnotatedClass annotatedClass) {

    String module = annotatedClass.getClassName() + "_Module";
    TypeSpec.Builder builder = classBuilder(module).addModifiers(PUBLIC);

    builder.addAnnotation(Module.class);

    for (int i = 0; i < annotatedClass.getExecutableElements().size(); i++) {
      ExecutableElement element = (ExecutableElement) annotatedClass.getExecutableElements().get(i);
      builder.addMethod(createProvidesMethod(annotatedClass, element));
    }

    return builder.build();
  }

  private MethodSpec createProvidesMethod(AnnotatedClass annotatedClass,
      ExecutableElement element) {
    MethodSpec.Builder builder = methodBuilder("provides" + element.getSimpleName())
        .addAnnotation(Provides.class);

    if (shouldBeNamed(element)) {
      builder.addAnnotation(AnnotationSpec
          .builder(Named.class)
          .addMember("value", "\"$N\"", resolveNamedValue(element))
          .build());
    }

    return builder.addModifiers(PUBLIC)
        .addParameter(ParameterSpec.builder(get(annotatedClass.getTypeElement()), "view").build())
        .addCode("return view.$N();", element.getSimpleName())
        .returns(get(element.getReturnType()))
        .build();
  }

  private static String resolveNamedValue(ExecutableElement element) {
    return element.getAnnotation(AutoProvides.class).value();
  }

  private static boolean shouldBeNamed(ExecutableElement element) {
    return !element.getAnnotation(AutoProvides.class).value().isEmpty();
  }
}
