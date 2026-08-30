package io.github.lolens.inno2;

import io.github.lolens.inno2.entity.TextPart;
import io.github.lolens.inno2.exception.TextReaderException;
import io.github.lolens.inno2.reader.TextFileReader;
import io.github.lolens.inno2.reader.impl.TextFileReaderImpl;
import io.github.lolens.inno2.service.TextService;
import io.github.lolens.inno2.service.impl.TextServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

public class Main {

  static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static final Path FILE_PATH;

  static {
    try {
      FILE_PATH = getFilePathFromResources("input.txt");
    } catch (Exception e) {
      throw new RuntimeException("Failed to initialize FILE_PATH", e);
    }
  }

  public static void main(String[] args) {
    TextService service = new TextServiceImpl();

    TextFileReader reader = new TextFileReaderImpl(FILE_PATH);
    String text;

    try {
      text = reader.readString();
    } catch (TextReaderException e) {
      throw new RuntimeException(e);
    }

    TextPart part = service.parse(text);

    String restored = service.restore(part);

    logger.info("Part .toString():\n{}", part.toString());

    logger.info("RESTORED:\n{}", restored);

  }

  private static Path getFilePathFromResources(String resourceName) throws FileNotFoundException, URISyntaxException {
    URL fileURL = Main.class.getClassLoader().getResource(resourceName);
    if (fileURL == null) throw new FileNotFoundException("Specified file is null.");
    return Path.of(fileURL.toURI());
  }

}
