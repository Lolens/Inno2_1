package io.github.lolens.inno2.reader;

import io.github.lolens.inno2.exception.TextReaderException;

import java.nio.file.Path;
import java.util.List;

public interface TextFileReader {

  String readString() throws TextReaderException;

  List<String> lines() throws TextReaderException;

}
