package io.github.lolens.inno2.exception;

public class TextReaderException extends Exception {
  public TextReaderException(String message) {
    super(message);
  }

  public TextReaderException() {
    super();
  }

  public TextReaderException(String message, Throwable cause) {
    super(message, cause);
  }

  public TextReaderException(Throwable cause) {
    super(cause);
  }

}
