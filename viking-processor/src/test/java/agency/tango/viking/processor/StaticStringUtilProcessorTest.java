package agency.tango.viking.processor;

import com.google.common.collect.ImmutableSet;
import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourcesSubjectFactory.javaSources;
public class StaticStringUtilProcessorTest {

  @Test
  public void generatesTypeAdapterFactory() {
    JavaFileObject source1 = JavaFileObjects.forSourceString("test.CheckinsFragment", ""
        + "package test;\n"
        + "import agency.tango.viking.annotations.AutoModule;\n"
        + "\n"
        + "@AutoModule\n"
        + "public class CheckinsFragment {\n"
        + "    \n"
        + "}");


    JavaFileObject expected = JavaFileObjects.forSourceString("test.CheckinsFragment_Module",
        ""
            + "package test;\n"
            + ""
            + "import dagger.Module;\n"
            + "import net.droidlabs.dagger.annotations.ActivityScope;"
            + "@ActivityScope\n"
            + "@Module\n"
            + "public class CheckinsFragment_Module {\n"
            + "  CheckinsFragment screen;\n"
            + "\n"
            + "  public CheckinsFragment_Module(CheckinsFragment screen) {\n"
            + "    this.screen = screen;\n"
            + "  }\n"
            + "\n"
            + "}");

    assertAbout(javaSources())
        .that(ImmutableSet.of(source1))
        .processedWith(new StaticStringUtilProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(expected);
  }
}