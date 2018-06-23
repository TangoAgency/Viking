package agency.tango.viking.processor;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
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
import javax.lang.model.type.TypeMirror;
import agency.tango.viking.annotations.AutoModule;
import agency.tango.viking.annotations.AutoProvides;
import agency.tango.viking.processor.annotation.AnnotationUtil;
import agency.tango.viking.processor.module.ComponentCodeBuilder;
import agency.tango.viking.processor.module.ModuleCodeGenerator;
import agency.tango.viking.processor.module.ModuleScopedCodeGenerator;
import agency.tango.viking.processor.module.ScreenMappingsBuilder;

import static com.squareup.javapoet.JavaFile.builder;
import static java.util.Collections.singleton;
import static javax.lang.model.SourceVersion.latestSupported;
import static javax.tools.Diagnostic.Kind.ERROR;

@AutoService(Processor.class)
public class VikingCodeProcessor extends AbstractProcessor {
  private static final String ANNOTATION = "@" + AutoModule.class.getSimpleName();
  private ProcessingEnvironment processingEnvironment;
  private Messager messager;

  private List<TypeMirror> typesWithScope = new ArrayList<>();

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

    handleAutoModule(roundEnv);
    return true;
  }

  private void handleAutoModule(RoundEnvironment roundEnv) {
    List<AnnotatedClass> annotatedClasses = new ArrayList<>();
    ListMultimap<TypeMirror, AnnotatedClass> annotatedClassesWithScopeAttribute =
        MultimapBuilder.hashKeys().arrayListValues().build();
    for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(AutoModule.class)) {

      TypeElement annotatedTypeElement = (TypeElement) annotatedElement;
      if (!isValidClass(annotatedTypeElement)) {
        return;
      }

      AnnotatedClass annotatedClass = buildAnnotatedClass(annotatedTypeElement);

      boolean saved = saveScopeRelated(annotatedClassesWithScopeAttribute, annotatedClass);
      if (!saved) {
        annotatedClasses.add(annotatedClass);
      }
    }

    try {
      Iterable<AnnotatedClass> allAnnotatedClasses = Iterables
          .concat(annotatedClasses, annotatedClassesWithScopeAttribute.values());

      generateModules(ImmutableSet.copyOf(allAnnotatedClasses).asList());
      generateScopeRelated(annotatedClassesWithScopeAttribute);
      generateMappings(annotatedClasses);
    } catch (IOException e) {
      messager.printMessage(ERROR, "Couldn't generate class");
    }
  }

  private boolean saveScopeRelated(
      ListMultimap<TypeMirror, AnnotatedClass> annotatedClassesWithScopeAttribute,
      AnnotatedClass annotatedClass) {
    List<TypeMirror> typeMirrors = AnnotationUtil.getValuesForAttribute("scopes", annotatedClass);
    for (TypeMirror typeMirror : typeMirrors) {
      addToTypesWithScope(typeMirror);
      addToScopedAnnotatedClass(annotatedClassesWithScopeAttribute, annotatedClass, typeMirror);
    }

    return !typeMirrors.isEmpty();
  }

  private void addToTypesWithScope(TypeMirror typeMirror) {
    for (TypeMirror type : typesWithScope) {
      if (type.equals(typeMirror)) {
        return;
      }
    }
    typesWithScope.add(typeMirror);
  }

  private void addToScopedAnnotatedClass(
      ListMultimap<TypeMirror, AnnotatedClass> annotatedClassesWithScopeAttribute,
      AnnotatedClass annotatedClass, TypeMirror typeMirror) {
    List<AnnotatedClass> classesWithinOneScope = annotatedClassesWithScopeAttribute.get(typeMirror);
    classesWithinOneScope.add(annotatedClass);
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

  private void generateModules(List<AnnotatedClass> annotatedClasses) throws IOException {
    for (AnnotatedClass annotatedClassClass : annotatedClasses) {
      TypeSpec moduleTypeSpec = new ModuleCodeGenerator(processingEnvironment).buildTypeSpec(
          annotatedClassClass);
      JavaFile moduleFile = builder(annotatedClassClass.getPackage(), moduleTypeSpec).build();
      moduleFile.writeTo(processingEnvironment.getFiler());
    }
  }

  private void generateScopeRelated(
      ListMultimap<TypeMirror, AnnotatedClass> annotatedClassesWithScopeAttribute)
      throws IOException {
    for (TypeMirror typeMirror : annotatedClassesWithScopeAttribute.keySet()) {
      List<AnnotatedClass> annotatedClasses = annotatedClassesWithScopeAttribute.get(typeMirror);

      TypeSpec moduleTypeSpec = new ModuleScopedCodeGenerator(Util.getSimpleTypeName(typeMirror))
          .buildTypeSpec(annotatedClasses.toArray(new AnnotatedClass[annotatedClasses.size()]));
      JavaFile moduleFile = builder(Util.getPackageName(typeMirror), moduleTypeSpec).build();
      moduleFile.writeTo(processingEnvironment.getFiler());
    }
  }

  private void generateMappings(List<AnnotatedClass> annotatedClasses) throws IOException {
    if (annotatedClasses.size() == 0) {
      return;
    }

    for (AnnotatedClass annotatedClassClass : annotatedClasses) {
      TypeSpec componentTypeSpec = new ComponentCodeBuilder().buildTypeSpec(annotatedClassClass);
      JavaFile componentFile = builder(annotatedClassClass.getPackage(),
          componentTypeSpec).build();
      componentFile.writeTo(processingEnvironment.getFiler());
    }

    JavaFile screenMappingsFile = builder("agency.tango.viking.di",
        new ScreenMappingsBuilder().buildTypeSpec(annotatedClasses, typesWithScope)).build();
    screenMappingsFile.writeTo(processingEnvironment.getFiler());
  }
}

