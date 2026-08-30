package io.github.lolens.inno2.exception;

public class TextServiceException extends RuntimeException {
  public TextServiceException(String message) {
    super(message);
  }

  public TextServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public TextServiceException(Throwable cause) {
    super(cause);
  }

  public TextServiceException() {

  }
}
