package agency.tango.viking.processor.module;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import net.droidlabs.dagger.annotations.ActivityScope;
import java.util.List;
import javax.lang.model.element.Modifier;
import agency.tango.viking.processor.AnnotatedClass;
import dagger.Module;

import static com.squareup.javapoet.ClassName.get;

public class ScreenMappingsBuilder {

  public TypeSpec buildTypeSpec(List<AnnotatedClass> annotatedClasses) {

    AnnotationSpec.Builder moduleAnnotationBuilder = AnnotationSpec.builder(Module.class);

    TypeSpec.Builder builder = TypeSpec.classBuilder("ScreenMappings")
        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
        .addAnnotation(moduleAnnotationBuilder.build());

    for (AnnotatedClass annotatedClass : annotatedClasses) {

      builder.addMethod(MethodSpec.methodBuilder(
          String.format("provide%s", annotatedClass.getClassName()))
          .addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC)
          .addAnnotation(
              AnnotationSpec.builder(get("dagger.android", "ContributesAndroidInjector"))
                  //.addMember("modules",
                  //    String.format("%s.class", annotatedClass.getClassName() + "_Module"))
                  .addMember("modules", "$T.class", get(annotatedClass.getPackage(),
                      annotatedClass.getClassName() + "_Module"))
                  .build()
          )
          .addAnnotation(AnnotationSpec.builder(get(ActivityScope.class)).build())
          .returns(get(annotatedClass.getPackage(), annotatedClass.getClassName()))
          .build());
    }

    return builder.build();
  }
}
