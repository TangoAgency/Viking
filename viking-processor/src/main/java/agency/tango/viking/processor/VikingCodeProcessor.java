package agency.tango.viking.processor;

import com.google.auto.service.AutoService;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
import agency.tango.viking.annotations.ProvidesViewModel;
import agency.tango.viking.processor.module.ComponentCodeBuilder;
import agency.tango.viking.processor.module.ModuleCodeGenerator;
import agency.tango.viking.processor.module.ModuleScopedCodeGenerator;
import agency.tango.viking.processor.module.ScreenBindingsModuleBuilder;
import agency.tango.viking.processor.module.ScreenMappingsBuilder;
import agency.tango.viking.processor.module.ViewModelMappingsBuilder;

import static agency.tango.viking.processor.Util.getAnnotation;
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
    handleAutoModule2(roundEnv);
    return true;
  }

  private void handleAutoModule(RoundEnvironment roundEnv) {
    List<AnnotatedClass> annotatedClassClasses = new ArrayList<>();
    ListMultimap<String, AnnotatedClass> annotatedClassesWithScopeAttribute =
        MultimapBuilder.treeKeys().arrayListValues().build();
    for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(AutoModule.class)) {

      TypeElement annotatedTypeElement = (TypeElement) annotatedElement;
      if (!isValidClass(annotatedTypeElement)) {
        return;
      }

      AnnotatedClass annotatedClass = buildAnnotatedClass(annotatedTypeElement);

      List<TypeMirror> typeMirrors = getScopeTypeMirrors(annotatedClass);
      for (TypeMirror typeMirror : typeMirrors) {
        addToTypesWithScope(typeMirror);
        addToScopedAnnotatedClass(annotatedClassesWithScopeAttribute, annotatedClass, typeMirror);
      }

      if (typeMirrors.isEmpty()) {
        annotatedClassClasses.add(annotatedClass);
      }
    }

    try {
      generateModules(ImmutableSet
          .copyOf(Iterables
              .concat(annotatedClassClasses, annotatedClassesWithScopeAttribute.values()))
          .asList());
      generateScopeRelated(annotatedClassesWithScopeAttribute);
      generateMappings(annotatedClassClasses);
    } catch (IOException e) {
      messager.printMessage(ERROR, "Couldn't generate class");
    }
    return;
  }

  private void addToTypesWithScope(TypeMirror typeMirror) {
    for (TypeMirror type : typesWithScope) {
      if (type.equals(typeMirror)) {
        return;
      }
    }
    typesWithScope.add(typeMirror);
  }

  private List<TypeMirror> getScopeTypeMirrors(AnnotatedClass annotatedClass) {
    Map<String, Object> parsedAnnotation = getAnnotation(AutoModule.class,
        annotatedClass.getTypeElement());

    List<TypeMirror> typeMirrors = new ArrayList<>();
    Object[] types = (Object[]) parsedAnnotation.get("scopes");
    for (Object obj : types) {
      TypeMirror typeMirror = (TypeMirror) obj;
      typeMirrors.add(typeMirror);
    }
    return typeMirrors;
  }

  private void addToScopedAnnotatedClass(
      ListMultimap<String, AnnotatedClass> annotatedClassesWithScopeAttribute,
      AnnotatedClass annotatedClass, TypeMirror typeMirror) {
    String fragmentModuleName = Iterables.getLast(
        Splitter.on(".").trimResults().split(TypeName.get(typeMirror).toString()));

    List<AnnotatedClass> classesWithinOneScope = annotatedClassesWithScopeAttribute.get(
        fragmentModuleName);
    classesWithinOneScope.add(annotatedClass);
  }

  private void handleAutoModule2(RoundEnvironment roundEnv) {
    ArrayList<AnnotatedClass> annotatedClassClasses = new ArrayList<>();
    for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(ProvidesViewModel.class)) {

      TypeElement annotatedClass = (TypeElement) annotatedElement;
      if (!isValidClass(annotatedClass)) {
        continue;
      }

      annotatedClassClasses.add(
          new AnnotatedClass(annotatedClass, Collections.<ExecutableElement>emptyList(),
              annotatedClass));
    }

    try {
      generateViewModelRelated(annotatedClassClasses);
    } catch (IOException e) {
      messager.printMessage(ERROR, "Couldn't generate class");
    }
    return;
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

    JavaFile screenBindingsFile = builder("agency.tango.viking.di",
        new ScreenBindingsModuleBuilder().buildTypeSpec(annotatedClasses)).build();
    screenBindingsFile.writeTo(processingEnvironment.getFiler());

    JavaFile screenMappingsFile = builder("agency.tango.viking.di",
        new ScreenMappingsBuilder().buildTypeSpec(annotatedClasses, typesWithScope)).build();
    screenMappingsFile.writeTo(processingEnvironment.getFiler());
  }

  private void generateScopeRelated(
      ListMultimap<String, AnnotatedClass> annotatedClassesWithScopeAttribute) throws IOException {
    for (String scopeName : annotatedClassesWithScopeAttribute.keySet()) {
      List<AnnotatedClass> annotatedClasses = annotatedClassesWithScopeAttribute.get(scopeName);

      TypeSpec moduleTypeSpec = new ModuleScopedCodeGenerator(scopeName)
          .buildTypeSpec(annotatedClasses.toArray(new AnnotatedClass[annotatedClasses.size()]));
      JavaFile moduleFile = builder(annotatedClasses.get(0).getPackage(), moduleTypeSpec).build();
      moduleFile.writeTo(processingEnvironment.getFiler());
    }
  }

  private void generateViewModelRelated(List<AnnotatedClass> annotatedClasses) throws IOException {
    if (annotatedClasses.size() == 0) {
      return;
    }

    JavaFile viewModelMappingsFile = builder("agency.tango.viking.di",
        new ViewModelMappingsBuilder().buildTypeSpec(annotatedClasses)).build();
    viewModelMappingsFile.writeTo(processingEnvironment.getFiler());
  }
}

