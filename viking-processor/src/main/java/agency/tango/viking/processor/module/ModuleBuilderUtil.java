package agency.tango.viking.processor.module;

import com.squareup.javapoet.CodeBlock;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.lang.model.type.TypeMirror;
import agency.tango.viking.annotations.AutoModule;
import agency.tango.viking.processor.AnnotatedClass;

import static agency.tango.viking.processor.Util.getAnnotation;
import static com.squareup.javapoet.ClassName.get;

public final class ModuleBuilderUtil {
  private ModuleBuilderUtil() {
  }

  public static CodeBlock buildModulesAttribute(AnnotatedClass annotatedClass) {
    Map<String, Object> parsedAnnotation = getAnnotation(AutoModule.class,
        annotatedClass.getTypeElement());

    Object[] types = (Object[]) parsedAnnotation.get("includes");
    List<TypeMirror> typeMirrors = new ArrayList<>();
    for (Object type : types) {
      TypeMirror typeMirror = (TypeMirror) type;
      typeMirrors.add(typeMirror);
    }

    CodeBlock.Builder builder = CodeBlock.builder().add("{$T.class",
            get(annotatedClass.getPackage(), annotatedClass.getClassName() + "_Module"));

    for (TypeMirror typeMirror : typeMirrors) {
      builder.add(", ").add("$T.class", get(typeMirror));
    }

    return builder.build();
  }
}
