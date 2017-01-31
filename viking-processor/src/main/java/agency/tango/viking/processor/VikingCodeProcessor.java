package agency.tango.viking.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import agency.tango.viking.annotations.AutoModule;
import agency.tango.viking.annotations.AutoProvides;
import agency.tango.viking.processor.module.ComponentCodeBuilder;
import agency.tango.viking.processor.module.ModuleCodeGenerator;
import agency.tango.viking.processor.module.ScreenBindingsModuleBuilder;

import static com.squareup.javapoet.JavaFile.builder;
import static java.util.Collections.singleton;
import static javax.lang.model.SourceVersion.latestSupported;
import static javax.tools.Diagnostic.Kind.ERROR;

@AutoService(Processor.class)
public class VikingCodeProcessor extends AbstractProcessor {
  private static final String ANNOTATION = "@" + AutoModule.class.getSimpleName();
  private ProcessingEnvironment processingEnvironment;
  private Messager messager;

  @Override
  public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    processingEnvironment = processingEnv;
    messager = processingEnv.getMessager();
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return singleton(AutoModule.class.getCanonicalName());
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return latestSupported();
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    ArrayList<AnnotatedClass> annotatedClassClasses = new ArrayList<>();
    for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(AutoModule.class)) {

      TypeElement annotatedClass = (TypeElement) annotatedElement;
      if (!isValidClass(annotatedClass)) {
        return true;
      }

      annotatedClassClasses.add(buildAnnotatedClass(annotatedClass));
    }

    try {
      generate(annotatedClassClasses);
    } catch (IOException e) {
      messager.printMessage(ERROR, "Couldn't generate class");
    }

    return true;
  }

  private boolean isValidClass(TypeElement annotatedClass) {

    if (!ClassValidator.isPublic(annotatedClass)) {
      String message = String.format("Classes annotated with %s must be public.",
          ANNOTATION);
      messager.printMessage(ERROR, message, annotatedClass);
      return false;
    }

    if (ClassValidator.isAbstract(annotatedClass)) {
      String message = String.format("Classes annotated with %s must not be abstract.",
          ANNOTATION);
      messager.printMessage(ERROR, message, annotatedClass);
      return false;
    }

    return true;
  }

  private AnnotatedClass buildAnnotatedClass(TypeElement annotatedClass) {
    ArrayList<ExecutableElement> variableNames = new ArrayList<>();
    for (Element element : annotatedClass.getEnclosedElements()) {

      AutoProvides pojo = element.getAnnotation(AutoProvides.class);
      if (pojo == null) {
        continue;
      }

      ExecutableElement variableElement = (ExecutableElement) element;
      variableNames.add(variableElement);
    }

    return new AnnotatedClass(annotatedClass, variableNames, annotatedClass);
  }

  private void generate(List<AnnotatedClass> annotatedClasses) throws IOException {
    if (annotatedClasses.size() == 0) {
      return;
    }

    for (AnnotatedClass annotatedClassClass : annotatedClasses) {

      TypeSpec componentTypeSpec = new ComponentCodeBuilder().buildTypeSpec(annotatedClassClass);
      JavaFile componentFile = builder(annotatedClassClass.getPackage(),
          componentTypeSpec).build();
      componentFile.writeTo(processingEnvironment.getFiler());

      TypeSpec moduleTypeSpec = new ModuleCodeGenerator(processingEnvironment).buildTypeSpec(
          annotatedClassClass);
      JavaFile moduleFile = builder(annotatedClassClass.getPackage(), moduleTypeSpec).build();
      moduleFile.writeTo(processingEnvironment.getFiler());

    }

    JavaFile javaFile = builder("agency.tango.viking.di",
        new ScreenBindingsModuleBuilder().buildTypeSpec(annotatedClasses)).build();
    javaFile.writeTo(processingEnvironment.getFiler());
  }
}

