package agency.tango.viking.processor;

import javax.lang.model.element.TypeElement;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PUBLIC;

final class ClassValidator {
  private ClassValidator() {
  }

  static boolean isPublic(TypeElement annotatedClass) {
    return annotatedClass.getModifiers().contains(PUBLIC);
  }

  static boolean isAbstract(TypeElement annotatedClass) {
    return annotatedClass.getModifiers().contains(ABSTRACT);
  }
}
