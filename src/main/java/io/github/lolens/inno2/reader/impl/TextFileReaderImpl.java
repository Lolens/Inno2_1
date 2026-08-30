package io.github.lolens.inno2.reader.impl;

import io.github.lolens.inno2.exception.TextReaderException;
import io.github.lolens.inno2.reader.TextFileReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class TextFileReaderImpl implements TextFileReader {

  private Path path;

  public TextFileReaderImpl() {

  }

  public TextFileReaderImpl(Path path) {
    this.path = path;
  }

  public void changeFilePath(Path path) {
    this.path = path;
  }

  public String readString() throws TextReaderException {
    try {
      return Files.readString(path);
    } catch (IOException e) {
      throw new TextReaderException("Encountered exception while reading a file", e);
    }
  }

  public List<String> lines() throws TextReaderException {

    try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
      return br.lines()
          .filter(s -> !s.isBlank())
          .collect(Collectors.toList());
    } catch (FileNotFoundException e) {
      throw new TextReaderException("Specified file does not exist or can't be read for other reasons", e);
    } catch (IOException ioe) {
      throw new TextReaderException("Encountered exception while reading a file", ioe);
    }
  }

}
