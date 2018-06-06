package agency.tango.viking.processor.module;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import net.droidlabs.dagger.annotations.ActivityScope;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import agency.tango.viking.annotations.AutoModule;
import agency.tango.viking.processor.AnnotatedClass;
import dagger.Module;

import static agency.tango.viking.processor.Util.getAnnotation;
import static com.squareup.javapoet.ClassName.get;

public class ScreenMappingsBuilder {

  public TypeSpec buildTypeSpec(List<AnnotatedClass> annotatedClasses, List<TypeMirror> typesWithScope) {

    AnnotationSpec.Builder moduleAnnotationBuilder = AnnotationSpec.builder(Module.class);

    TypeSpec.Builder builder = TypeSpec.classBuilder("ScreenMappings")
        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
        .addAnnotation(moduleAnnotationBuilder.build());

    for (AnnotatedClass annotatedClass : annotatedClasses) {
      Map<String, Object> parsedAnnotation = getAnnotation(AutoModule.class,
          annotatedClass.getTypeElement());

      Object[] types = (Object[]) parsedAnnotation.get("includes");
      List<TypeMirror> typeMirrors = new ArrayList<>();
      for (Object type : types) {
        TypeMirror typeMirror = (TypeMirror) type;
        typeMirrors.add(typeMirror);
      }

      AnnotationSpec.Builder annotationBuilder =
          AnnotationSpec.builder(get("dagger.android", "ContributesAndroidInjector"));
      //.addMember("modules",
      //    String.format("%s.class", annotatedClass.getClassName() + "_Module"))

      String modules = String.format("{%s.class",
          get(annotatedClass.getPackage(), annotatedClass.getClassName() + "_Module").toString());
      StringBuilder stringBuilder = new StringBuilder(modules);
      for (TypeMirror typeMirror : typeMirrors) {
        stringBuilder.append(", ").append(TypeName.get(typeMirror).toString()).append(".class");
      }

      for (TypeMirror type : typesWithScope) {
        if(annotatedClass.getTypeMirror().equals(type)) {
          stringBuilder.append(", ").append(TypeName.get(type).toString()).append("Fragments_Module.class");
        }
      }

      modules = stringBuilder.append("}").toString();
      annotationBuilder.addMember("modules", modules);

      builder.addMethod(MethodSpec.methodBuilder(
          String.format("provide%s", annotatedClass.getClassName()))
          .addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC)
          .addAnnotation(
              annotationBuilder.build()
          )
          .addAnnotation(AnnotationSpec.builder(get(ActivityScope.class)).build())
          .returns(get(annotatedClass.getPackage(), annotatedClass.getClassName()))
          .build());
    }

    return builder.build();
  }
}
