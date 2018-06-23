package agency.tango.viking.processor.module;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import agency.tango.viking.processor.AnnotatedClass;
import agency.tango.viking.processor.ExtendedCodeBuilder;
import agency.tango.viking.processor.annotation.AnnotationAttributesBuilder;
import agency.tango.viking.processor.annotation.AnnotationUtil;
import dagger.Module;

import static com.squareup.javapoet.ClassName.get;
import static com.squareup.javapoet.MethodSpec.methodBuilder;
import static com.squareup.javapoet.TypeSpec.classBuilder;
import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PUBLIC;

public class ModuleScopedCodeGenerator implements ExtendedCodeBuilder {
  private String moduleName;

  public ModuleScopedCodeGenerator(String moduleName) {
    this.moduleName = moduleName;
  }

  @Override
  public TypeSpec buildTypeSpec(AnnotatedClass... annotatedClasses) {
    String module = moduleName + "Fragments_Module";
    TypeSpec.Builder builder = classBuilder(module).addModifiers(PUBLIC, ABSTRACT);

    builder.addAnnotation(Module.class);

    for (AnnotatedClass annotatedClass : annotatedClasses) {
      builder.addMethod(buildProvidesMethod(annotatedClass));
    }

    return builder.build();
  }

  private MethodSpec buildProvidesMethod(AnnotatedClass annotatedClass) {
    return methodBuilder("provides" + annotatedClass.getClassName())
        .addAnnotation(buildDaggerAnnotation(annotatedClass))
        .addModifiers(PUBLIC, ABSTRACT)
        .returns(get(annotatedClass.getTypeElement()))
        .build();
  }

  private AnnotationSpec buildDaggerAnnotation(AnnotatedClass annotatedClass) {
    AnnotationSpec.Builder annotationBuilder =
        AnnotationSpec.builder(get("dagger.android", "ContributesAndroidInjector"));

    CodeBlock modules = new AnnotationAttributesBuilder()
        .addAttribute(annotatedClass.getPackage(), annotatedClass.getClassName() + "_Module")
        .addAttributes(AnnotationUtil.getValuesForAttribute("includes", annotatedClass))
        .build();

    annotationBuilder.addMember("modules", modules);

    return annotationBuilder.build();
  }
}
