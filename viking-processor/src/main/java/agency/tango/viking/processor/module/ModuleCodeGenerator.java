package agency.tango.viking.processor.module;

import com.google.common.base.Joiner;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import net.droidlabs.dagger.annotations.ActivityScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import agency.tango.viking.annotations.AutoModule;
import agency.tango.viking.di.ScreenModule;
import agency.tango.viking.processor.AnnotatedClass;
import agency.tango.viking.processor.CodeBuilder;
import dagger.Module;

import static agency.tango.viking.processor.Util.getAnnotation;
import static com.squareup.javapoet.ClassName.get;
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

    builder.addAnnotation(AnnotationSpec.builder(get(ActivityScope.class)).build());

    builder.addAnnotation(Module.class);

    Map<String, Object> parsedAnnotation = getAnnotation(AutoModule.class,
        annotatedClass.getTypeElement());

    ClassName className;
    Object typeMirrors = parsedAnnotation.get("superClass");

    if (typeMirrors instanceof Class<?>) {
      className = ClassName.get((Class<?>) typeMirrors);
    } else {
      className = ClassName.bestGuess(typeMirrors.toString());
    }

    if (className.equals(get(Object.class))) {

      builder.superclass(
          ParameterizedTypeName.get(ClassName.get(ScreenModule.class),
              get(annotatedClass.getTypeElement())));

      MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder();
      constructorBuilder.addParameter(get("android.content", "Context"), "context");
      constructorBuilder.addParameter(TypeVariableName.get(annotatedClass.getTypeMirror()),
          "screen");
      constructorBuilder.addStatement("super($N, $N)", "context", "screen");

      builder.addMethod(constructorBuilder.build());

    } else {
      TypeElement typeElement = processingEnvironment.getElementUtils()
          .getTypeElement(className.toString());

      TypeMirror typeMirror = typeElement
          .asType();

      Element baseModule = processingEnvironment.getTypeUtils().asElement(typeMirror);

      builder.superclass(className);

      for (Element enclosed : baseModule.getEnclosedElements()) {
        if (enclosed.getKind() == ElementKind.CONSTRUCTOR) {
          ExecutableElement constructorElement = (ExecutableElement) enclosed;
          if (constructorElement.getParameters().size() > 0 && constructorElement.getModifiers()
              .contains(Modifier.PUBLIC)) {
            MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder();

            List<String> paramNames = new ArrayList<>();

            for (int i = 0; i < constructorElement.getParameters().size(); i++) {
              VariableElement variableElement = constructorElement.getParameters().get(i);

              constructorBuilder.addParameter(get(variableElement.asType()),
                  variableElement.getSimpleName().toString());
              paramNames.add(variableElement.getSimpleName().toString());
            }

            builder.addMethod(constructorBuilder.addModifiers(Modifier.PUBLIC)
                .addStatement("super($L)", Joiner.on(", ").join(paramNames))
                .build());
          }
        }
      }
    }

    return builder.build();

  }
}
