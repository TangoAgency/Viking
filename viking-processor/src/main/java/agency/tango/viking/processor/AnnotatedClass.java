package agency.tango.viking.processor;

import java.util.List;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

public class AnnotatedClass<T> {

  private final String annotatedClassName;
  private final List<ExecutableElement> executableElements;
  private final TypeElement typeElement;
  private TypeElement annotatedClass;

  public AnnotatedClass(TypeElement typeElement, List<ExecutableElement> executableElements,
      TypeElement annotatedClass) {
    annotatedClassName = typeElement.getSimpleName().toString();
    this.executableElements = executableElements;
    this.typeElement = typeElement;
    this.annotatedClass = annotatedClass;
  }

  public String getClassName() {
    return annotatedClassName;
  }

  public TypeMirror getTypeMirror() {
    return typeElement.asType();
  }

  public TypeElement getTypeElement() {
    return annotatedClass;
  }

  public List<ExecutableElement> getExecutableElements() {
    return executableElements;
  }

  public String getPackage() {
    return Util.getPackage(getTypeElement()).getQualifiedName().toString();
  }

}
