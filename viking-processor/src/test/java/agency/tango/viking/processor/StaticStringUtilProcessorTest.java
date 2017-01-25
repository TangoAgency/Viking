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
    JavaFileObject source1 = JavaFileObjects.forSourceString("test.CheckinsFragment",
        "package test;\n"
            + "import agency.tango.viking.annotations.AutoModule;\n"
            + "\n"
            + "@AutoModule\n"
            + "public class CheckinsFragment {\n"
            + "    \n"
            + "}");

    JavaFileObject expected = JavaFileObjects.forSourceString("test.CheckinsFragment_Module",
        "package test;\n"
            + "\n"
            + "import agency.tango.viking.di.ScreenModule;\n"
            + "import android.content.Context;\n"
            + "import dagger.Module;\n"
            + "import net.droidlabs.dagger.annotations.ActivityScope;\n"
            + "\n"
            + "@ActivityScope\n"
            + "@Module\n"
            + "public class CheckinsFragment_Module extends ScreenModule<CheckinsFragment> {\n"
            + "  CheckinsFragment_Module(Context context, CheckinsFragment screen) {\n"
            + "    super(context, screen);\n"
            + "  }\n"
            + "}");

    assertAbout(javaSources())
        .that(ImmutableSet.of(source1))
        .processedWith(new StaticStringUtilProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(expected);
  }

  @Test
  public void generatesTypeAdapterFactory2() {

    JavaFileObject baseModule = JavaFileObjects.forSourceString("test.BaseModule",
        "package test;\n"
            + "\n"
            + "public class BaseModule {\n"
            + "  public BaseModule(String a, String b) {\n"
            + "\n"
            + "  }\n"
            + "}"
    );

    JavaFileObject source1 = JavaFileObjects.forSourceString("test.CheckinsFragment",
        "package test;\n"
            + "import agency.tango.viking.annotations.AutoModule;\n"
            + "import test.BaseModule;\n"
            + "\n"
            + "@AutoModule(superClass = BaseModule.class)\n"
            + "public class CheckinsFragment {\n"
            + "    \n"
            + "}");

    JavaFileObject expected = JavaFileObjects.forSourceString("test.CheckinsFragment_Module",
        "package test;\n"
            + "\n"
            + "import dagger.Module;\n"
            + "import java.lang.String;\n"
            + "import net.droidlabs.dagger.annotations.ActivityScope;\n"
            + "\n"
            + "@ActivityScope\n"
            + "@Module\n"
            + "public class CheckinsFragment_Module extends BaseModule {\n"
            + "  public CheckinsFragment_Module(String a, String b) {\n"
            + "    super(a, b);\n"
            + "  }\n"
            + "}");

    assertAbout(javaSources())
        .that(ImmutableSet.of(baseModule, source1))
        .processedWith(new StaticStringUtilProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(expected);
  }

}