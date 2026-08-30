package io.github.lolens.inno2.reader.impl;

import io.github.lolens.inno2.exception.TextReaderException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TextFileReaderImplTest {

  @Test
  void readStringShouldReturnFullFileContent(@TempDir Path tempDir) throws IOException, TextReaderException {
    Path file = tempDir.resolve("sample.txt");
    Files.writeString(file, "Hello world.\n\nSecond paragraph.");

    TextFileReaderImpl reader = new TextFileReaderImpl(file);

    assertEquals("Hello world.\n\nSecond paragraph.", reader.readString());
  }

  @Test
  void linesShouldFilterOutBlankLines(@TempDir Path tempDir) throws IOException, TextReaderException {
    Path file = tempDir.resolve("sample.txt");
    Files.writeString(file, "First line\n\nSecond line\n   \nThird line");

    TextFileReaderImpl reader = new TextFileReaderImpl(file);
    List<String> lines = reader.lines();

    assertEquals(List.of("First line", "Second line", "Third line"), lines);
  }

  @Test
  void readStringShouldThrowTextReaderExceptionWhenFileMissing(@TempDir Path tempDir) {
    Path missing = tempDir.resolve("missing.txt");
    TextFileReaderImpl reader = new TextFileReaderImpl(missing);

    assertThrows(TextReaderException.class, reader::readString);
  }

  @Test
  void linesShouldThrowTextReaderExceptionWhenFileMissing(@TempDir Path tempDir) {
    Path missing = tempDir.resolve("missing.txt");
    TextFileReaderImpl reader = new TextFileReaderImpl(missing);

    assertThrows(TextReaderException.class, reader::lines);
  }

  @Test
  void changeFilePathShouldSwitchTargetFile(@TempDir Path tempDir) throws IOException, TextReaderException {
    Path first = tempDir.resolve("first.txt");
    Path second = tempDir.resolve("second.txt");
    Files.writeString(first, "First file");
    Files.writeString(second, "Second file");

    TextFileReaderImpl reader = new TextFileReaderImpl(first);
    reader.changeFilePath(second);

    assertEquals("Second file", reader.readString());
  }
}