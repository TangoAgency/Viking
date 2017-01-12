package agency.tango.viking.processor.module;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;

import agency.tango.viking.processor.AnnotatedClass;
import agency.tango.viking.processor.CodeBuilder;
import dagger.Module;
import dagger.Provides;

import static com.squareup.javapoet.ClassName.get;
import static com.squareup.javapoet.MethodSpec.methodBuilder;
import static com.squareup.javapoet.TypeSpec.classBuilder;
import static javax.lang.model.element.Modifier.PUBLIC;

public class ModuleCodeGenerator implements CodeBuilder {
  @Override
  public TypeSpec buildTypeSpec(AnnotatedClass annotatedClass) {

    String module = annotatedClass.getClassName() + "_Module";
    TypeSpec.Builder builder = classBuilder(module)
        .addModifiers(PUBLIC);

    builder.addAnnotation(
        AnnotationSpec.builder(get("net.droidlabs.dagger.annotations", "ActivityScope")).build());

    builder.addAnnotation(Module.class);

    builder.addField(FieldSpec.builder(get(annotatedClass.getTypeElement()), "screen").build());

    //        Element element1 = processingEnv.getTypeUtils().asElement(value);
    //
    //        for (Element enclosed : element1.getEnclosedElements())
    //        {
    //            if (enclosed.getKind() == ElementKind.CONSTRUCTOR)
    //            {
    //                ExecutableElement constructorElement = (ExecutableElement) enclosed;
    //                if (constructorElement.getParameters().size() > 0 && constructorElement.getModifiers().contains(Modifier.PUBLIC))
    //                {
    //                    MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder();
    //
    //
    //                    List<String> paramNames = new ArrayList<>();
    //
    //                    for (int i = 0; i < constructorElement.getParameters().size(); i++)
    //                    {
    //                        VariableElement variableElement = constructorElement.getParameters().get(i);
    //                        //constructorBuilder.addParameter(TypeVariableName.get(variableElement.asType().toString()), variableElement.getSimpleName().toString());
    //                        paramNames.add(variableElement.getSimpleName().toString());
    //                    }
    //
    //                    constructorBuilder.addParameter(TypeVariableName.get(annotatedClass.getClassName()), "screen");
    //
    //                    builder.addMethod(constructorBuilder.addModifiers(Modifier.PUBLIC)
    //                           // .addStatement("super($L)", Joiner.on(",").join(paramNames))
    //                            .addStatement("this.$N = $N", "screen", "screen")
    //                            .build());
    //                }
    //            }
    //
    //
    //        }

    MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder();
    constructorBuilder.addParameter(TypeVariableName.get(annotatedClass.getClassName()),
        "screen");
    builder.addMethod(constructorBuilder.addModifiers(Modifier.PUBLIC)
        // .addStatement("super($L)", Joiner.on(",").join(paramNames))
        .addStatement("this.$N = $N", "screen", "screen")
        .build());

    for (int i = 0; i < annotatedClass.getExecutableElements().size(); i++) {
      ExecutableElement element = (ExecutableElement) annotatedClass.getExecutableElements().get(i);

      builder.addMethod(methodBuilder("provides" + element.getSimpleName())
          .addAnnotation(Provides.class)
          .addModifiers(PUBLIC)
          .addCode("return this.screen.$N();", element.getSimpleName())
          .returns(TypeName.get(element.getReturnType()))
          .build());
    }

    return builder.build();

  }
}
