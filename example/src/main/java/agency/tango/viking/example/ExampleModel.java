package agency.tango.viking.example;

public class ExampleModel {
  private String description;
  private String title;

  public ExampleModel(String title, String description) {
    this.title = title;
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public String getTitle() {
    return title;
  }
}
