package magis5.magis5challenge.utils;

import java.io.IOException;
import java.nio.file.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class FileUtils {
  @Autowired private ResourceLoader resourceLoader;

  public String readResourceFile(String fileName) throws IOException {
    var file = resourceLoader.getResource("classpath:%s".formatted(fileName)).getFile();
    return new String(Files.readAllBytes(file.toPath()));
  }
}
