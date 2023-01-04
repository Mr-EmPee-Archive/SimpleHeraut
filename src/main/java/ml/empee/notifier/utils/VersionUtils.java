package ml.empee.notifier.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VersionUtils {

  /**
   * @return Greater then 0 if version1 is greater than version2
   */
  public static int compareVersions(String version1, String version2) {
    String[] version1Parts = version1.split("\\.");
    String[] version2Parts = version2.split("\\.");

    int length = Math.max(version1Parts.length, version2Parts.length);
    for (int i = 0; i < length; i++) {
      int v1 = i < version1Parts.length ? Integer.parseInt(version1Parts[i]) : 0;
      int v2 = i < version2Parts.length ? Integer.parseInt(version2Parts[i]) : 0;
      if (v1 != v2) {
        return v1 - v2;
      }
    }
    return 0;
  }

}
