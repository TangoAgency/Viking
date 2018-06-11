package agency.tango.viking.processor.annotation;

import com.squareup.javapoet.CodeBlock;
import java.util.List;
import javax.lang.model.type.TypeMirror;

import static com.squareup.javapoet.ClassName.get;

public class AnnotationAttributesBuilder {
  private final CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();
  private boolean isNotFirst = false;

  public AnnotationAttributesBuilder addAttribute(TypeMirror typeMirror) {
    addComma();
    codeBlockBuilder.add("$T.class", get(typeMirror));
    return this;
  }

  public AnnotationAttributesBuilder addAttribute(String packageName, String className) {
    addComma();
    codeBlockBuilder.add("$T.class", get(packageName, className));
    return this;
  }

  public AnnotationAttributesBuilder addAttributes(List<TypeMirror> typeMirrors) {
    for (TypeMirror typeMirror : typeMirrors) {
      addComma();
      codeBlockBuilder.add("$T.class", get(typeMirror));
    }
    return this;
  }

  public CodeBlock build() {
    return CodeBlock.builder()
        .add("{")
        .add(codeBlockBuilder.build())
        .add("}")
        .build();
  }

  private void addComma() {
    if (isNotFirst) {
      codeBlockBuilder.add(", ");
    }
    isNotFirst = true;
  }
}
