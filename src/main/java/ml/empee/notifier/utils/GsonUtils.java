package ml.empee.notifier.utils;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ml.empee.notifier.model.Update;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GsonUtils {

  private static Gson gson = new Gson();

  public static Update[] parseUpdates(InputStream stream) {
    return gson.fromJson(new BufferedReader(new InputStreamReader(stream)), Update[].class);
  }

}
