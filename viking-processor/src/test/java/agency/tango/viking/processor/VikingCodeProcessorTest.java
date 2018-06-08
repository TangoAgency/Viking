package agency.tango.viking.processor;

import com.google.common.collect.ImmutableSet;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Ignore;
import org.junit.Test;
import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourcesSubjectFactory.javaSources;

public class VikingCodeProcessorTest {
  @Test
  public void vikingCodeProcessor_unScopedFragment_generatesScreenMappingsModule() {
    JavaFileObject testActivity = JavaFileObjects.forSourceString("test.TestActivity", getTestActivity());
    JavaFileObject testFragment = JavaFileObjects.forSourceString("test.TestFragment", getTestFragment());

    JavaFileObject expectedScreenMappings = JavaFileObjects.forSourceString("agency.tango.viking.di.ScreenMappings",
        "package agency.tango.viking.di;\n"
            + "import dagger.Module;\n"
            + "import dagger.android.ContributesAndroidInjector;\n"
            + "import net.droidlabs.dagger.annotations.ActivityScope;\n"
            + "import test.TestActivity;\n"
            + "import test.TestFragment;\n"
            + "\n"
            + "@Module\n"
            + "public abstract class ScreenMappings {\n"
            + "  @ContributesAndroidInjector(\n"
            + "      modules = {test.TestActivity_Module.class}\n"
            + "  )\n"
            + "  @ActivityScope\n"
            + "  public abstract TestActivity provideTestActivity();\n"
            + "\n"
            + "  @ContributesAndroidInjector(\n"
            + "      modules = {test.TestFragment_Module.class}\n"
            + "  )\n"
            + "  @ActivityScope\n"
            + "  public abstract TestFragment provideTestFragment();"
            + "}");

    assertAbout(javaSources())
        .that(ImmutableSet.of(testActivity, testFragment))
        .processedWith(new VikingCodeProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(expectedScreenMappings);
  }

  @Test
  public void vikingCodeProcessor_scopedFragment_generatesActivityFragmentsAndScreenMappingsModules() {
    JavaFileObject testActivity = JavaFileObjects.forSourceString("test.TestActivity", getTestActivity());
    JavaFileObject testFragment = JavaFileObjects.forSourceString("test.TestFragment",
        getTestFragmentWithScopesAttribute("TestActivity.class"));

    JavaFileObject expectedActivityFragmentsModule = JavaFileObjects.forSourceString("test.ActivityFragments_Module",
        "package test;\n"
            + "import dagger.Module;\n"
            + "import dagger.android.ContributesAndroidInjector;\n"
            + "\n"
            + "@Module\n"
            + "public abstract class TestActivityFragments_Module {\n"
            + "  @ContributesAndroidInjector\n"
            + "  public abstract TestFragment providesTestFragment();\n"
            + "}");

    JavaFileObject expectedScreenMappings = JavaFileObjects.forSourceString("agency.tango.viking.di.ScreenMappings",
        "package agency.tango.viking.di;\n"
            + "import dagger.Module;\n"
            + "import dagger.android.ContributesAndroidInjector;\n"
            + "import net.droidlabs.dagger.annotations.ActivityScope;\n"
            + "import test.TestActivity;\n"
            + "\n"
            + "@Module\n"
            + "public abstract class ScreenMappings {\n"
            + "  @ContributesAndroidInjector(\n"
            + "      modules = {test.TestActivity_Module.class, test.TestActivityFragments_Module.class}\n"
            + "  )\n"
            + "  @ActivityScope\n"
            + "  public abstract TestActivity provideTestActivity();\n"
            + "}");

    assertAbout(javaSources())
        .that(ImmutableSet.of(testActivity, testFragment))
        .processedWith(new VikingCodeProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(expectedActivityFragmentsModule, expectedScreenMappings);
  }

  @Test
  public void vikingCodeProcessor_doubleScopedFragment_generatesActivitiesFragmentsAndScreenMappingsModules() {
    JavaFileObject testActivity = JavaFileObjects.forSourceString("test.TestActivity", getTestActivity());
    JavaFileObject secondTestActivity = JavaFileObjects.forSourceString(
        "test.SecondTestActivity", getSecondTestActivity());
    JavaFileObject testFragment = JavaFileObjects.forSourceString(
        "test.TestFragment", getTestFragmentWithScopesAttribute("TestActivity.class", "SecondTestActivity.class"));

    JavaFileObject expectedActivityFragmentsModule = JavaFileObjects.forSourceString("test.ActivityFragments_Module",
        "package test;\n"
            + "import dagger.Module;\n"
            + "import dagger.android.ContributesAndroidInjector;\n"
            + "\n"
            + "@Module\n"
            + "public abstract class TestActivityFragments_Module {\n"
            + "  @ContributesAndroidInjector\n"
            + "  public abstract TestFragment providesTestFragment();\n"
            + "}");

    JavaFileObject expectedSecondActivityFragmentsModule = JavaFileObjects.forSourceString(
        "test.SecondActivityFragments_Module",
        "package test;\n"
            + "import dagger.Module;\n"
            + "import dagger.android.ContributesAndroidInjector;\n"
            + "\n"
            + "@Module\n"
            + "public abstract class SecondTestActivityFragments_Module {\n"
            + "  @ContributesAndroidInjector\n"
            + "  public abstract TestFragment providesTestFragment();\n"
            + "}");

    JavaFileObject expectedScreenMappings = JavaFileObjects.forSourceString("agency.tango.viking.di.ScreenMappings",
        "package agency.tango.viking.di;\n"
            + "import dagger.Module;\n"
            + "import dagger.android.ContributesAndroidInjector;\n"
            + "import net.droidlabs.dagger.annotations.ActivityScope;\n"
            + "import test.SecondTestActivity;\n"
            + "import test.TestActivity;\n"
            + "\n"
            + "@Module\n"
            + "public abstract class ScreenMappings {\n"
            + "  @ContributesAndroidInjector(\n"
            + "      modules = {test.TestActivity_Module.class, test.TestActivityFragments_Module.class}\n"
            + "  )\n"
            + "  @ActivityScope\n"
            + "  public abstract TestActivity provideTestActivity();\n"
            + "\n"
            + "  @ContributesAndroidInjector(\n"
            + "      modules = {test.SecondTestActivity_Module.class, test.SecondTestActivityFragments_Module.class}\n"
            + "  )\n"
            + "  @ActivityScope\n"
            + "  public abstract SecondTestActivity provideSecondTestActivity();\n"
            + "}");

    assertAbout(javaSources())
        .that(ImmutableSet.of(testActivity, secondTestActivity, testFragment))
        .processedWith(new VikingCodeProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(
            expectedActivityFragmentsModule, expectedSecondActivityFragmentsModule, expectedScreenMappings);
  }

  @Test
  public void vikingCodeProcessor_includesModule_generatesScreenMappingsModule() {
    JavaFileObject testModule = JavaFileObjects.forSourceString("test.TestModule",
        "package test;\n"
            + "import dagger.Module;\n"
            + "\n"
            + "@Module\n"
            + "public class TestModule {\n"
            + "    \n"
            + "}");

    JavaFileObject testActivity = JavaFileObjects.forSourceString("test.TestActivity",
        "package test;\n"
            + "import agency.tango.viking.annotations.AutoModule;\n"
            + "\n"
            + "@AutoModule(includes = {TestModule.class})\n"
            + "public class TestActivity {\n"
            + "    \n"
            + "}");

    JavaFileObject expectedScreenMappings = JavaFileObjects.forSourceString("agency.tango.viking.di.ScreenMappings",
        "package agency.tango.viking.di;\n"
            + "import dagger.Module;\n"
            + "import dagger.android.ContributesAndroidInjector;\n"
            + "import net.droidlabs.dagger.annotations.ActivityScope;\n"
            + "import test.TestActivity;\n"
            + "\n"
            + "@Module\n"
            + "public abstract class ScreenMappings {\n"
            + "  @ContributesAndroidInjector(\n"
            + "      modules = {test.TestActivity_Module.class, test.TestModule.class}\n"
            + "  )\n"
            + "  @ActivityScope\n"
            + "  public abstract TestActivity provideTestActivity();\n"
            + "}");

    assertAbout(javaSources())
        .that(ImmutableSet.of(testActivity, testModule))
        .processedWith(new VikingCodeProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(expectedScreenMappings);
  }

  private String getTestFragment() {
    return "package test;\n"
        + "import agency.tango.viking.annotations.AutoModule;\n"
        + "\n"
        + "@AutoModule\n"
        + "public class TestFragment {\n"
        + "    \n"
        + "}";
  }

  private String getTestActivity() {
    return "package test;\n"
        + "import agency.tango.viking.annotations.AutoModule;\n"
        + "\n"
        + "@AutoModule\n"
        + "public class TestActivity {\n"
        + "    \n"
        + "}";
  }

  private String getSecondTestActivity() {
    return "package test;\n"
        + "import agency.tango.viking.annotations.AutoModule;\n"
        + "\n"
        + "@AutoModule\n"
        + "public class SecondTestActivity {\n"
        + "    \n"
        + "}";
  }

  private String getTestFragmentWithScopesAttribute(String... scopesAttributes) {
    String autoModule = "@AutoModule";
    String testFragment = getTestFragment();

    StringBuilder scopesBuilder = new StringBuilder("(scopes = {");
    for (String scopeAttribute : scopesAttributes) {
      scopesBuilder.append(String.format("%s,", scopeAttribute));
    }
    scopesBuilder.append("})");

    return new StringBuilder(testFragment)
        .insert(testFragment.indexOf(autoModule) + autoModule.length(), scopesBuilder.toString())
        .toString();
  }

  @Ignore
  @Test
  public void generatesTypeAdapterFactory() {

    JavaFileObject checkingFragment = JavaFileObjects.forSourceString("test.CheckinsFragment",
        "package test;\n"
            + "import agency.tango.viking.annotations.AutoModule;\n"
            + "\n"
            + "@AutoModule\n"
            + "public class CheckinsFragment {\n"
            + "    \n"
            + "}");

    JavaFileObject expectedCheckinsFragmentModule = JavaFileObjects.forSourceString(
        "test.CheckinsFragment_Module",
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
        .that(ImmutableSet.of(checkingFragment))
        .processedWith(new VikingCodeProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(expectedCheckinsFragmentModule);
  }

  @Ignore
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

    JavaFileObject checkingFragment = JavaFileObjects.forSourceString("test.CheckinsFragment",
        "package test;\n"
            + "import agency.tango.viking.annotations.AutoModule;\n"
            + "import test.BaseModule;\n"
            + "\n"
            + "@AutoModule(superClass = BaseModule.class)\n"
            + "public class CheckinsFragment {\n"
            + "    \n"
            + "}");

    JavaFileObject expectedCheckinsFragmentModule = JavaFileObjects.forSourceString(
        "test.CheckinsFragment_Module",
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
        .that(ImmutableSet.of(baseModule, checkingFragment))
        .processedWith(new VikingCodeProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(expectedCheckinsFragmentModule);
  }
}