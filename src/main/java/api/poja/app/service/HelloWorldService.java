package api.poja.app.service;

import api.poja.app.file.bucket.BucketComponent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HelloWorldService {
  private final BucketComponent bucket;

  private static void writeMessageIntoFile(String message, File file) throws IOException {
    var writer = new FileWriter(file);
    writer.write(message);
    writer.close();
  }

  @SneakyThrows
  public String uploadHelloWorldMessage(String name) {
    var fileSuffix = ".txt";
    var filePrefix = "hello-world-" + name;
    var bucketKey = filePrefix + fileSuffix;
    var fileToUpload = File.createTempFile(filePrefix, fileSuffix);
    writeMessageIntoFile("Hello World from poja " + name + "!", fileToUpload);

    bucket.upload(fileToUpload, bucketKey);
    return bucket.presign(bucketKey, Duration.ofMinutes(2)).toString();
  }
}
