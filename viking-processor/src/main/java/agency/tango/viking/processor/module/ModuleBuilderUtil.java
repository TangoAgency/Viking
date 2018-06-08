package agency.tango.viking.processor.module;

import com.squareup.javapoet.TypeName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.lang.model.type.TypeMirror;
import agency.tango.viking.annotations.AutoModule;
import agency.tango.viking.processor.AnnotatedClass;

import static agency.tango.viking.processor.Util.getAnnotation;
import static com.squareup.javapoet.ClassName.get;

public class ModuleBuilderUtil {
  private ModuleBuilderUtil() {
  }

  public static String buildModulesAttribute(AnnotatedClass annotatedClass) {
    Map<String, Object> parsedAnnotation = getAnnotation(AutoModule.class,
        annotatedClass.getTypeElement());

    Object[] types = (Object[]) parsedAnnotation.get("includes");
    List<TypeMirror> typeMirrors = new ArrayList<>();
    for (Object type : types) {
      TypeMirror typeMirror = (TypeMirror) type;
      typeMirrors.add(typeMirror);
    }

    String modules = String.format("{%s.class",
        get(annotatedClass.getPackage(), annotatedClass.getClassName() + "_Module").toString());
    StringBuilder stringBuilder = new StringBuilder(modules);
    for (TypeMirror typeMirror : typeMirrors) {
      stringBuilder.append(", ").append(TypeName.get(typeMirror).toString()).append(".class");
    }

    return stringBuilder.append("}").toString();
  }
}
