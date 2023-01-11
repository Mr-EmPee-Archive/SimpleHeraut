package ml.empee.notifier.model;

import java.util.Base64;
import lombok.Data;
import ml.empee.notifier.ReportLevel;

@Data
public class Update {

  private String title;
  private String description;

  public String getVersion() {
    return title.split(" - ")[0].replace("Version: ", "");
  }

  public ReportLevel getPriority() {
    String[] titles = title.split(" - ");
    String priority = titles[1].replace("Priority: ", "");

    switch(priority.toUpperCase()) {
      case "HIGH":
        return ReportLevel.IMPORTANT;
      case "MEDIUM":
        return ReportLevel.BUG_FIX;
      default:
        return ReportLevel.UPDATE;
    }
  }

  public String getDescription() {
    return new String(Base64.getDecoder().decode(this.description)).replace("<br>", "");
  }

  public String toString() {
    return title + "\n" + getDescription();
  }

}
