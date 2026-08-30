package io.github.lolens.inno2.parser;

import io.github.lolens.inno2.entity.TextPart;

public abstract class AbstractTextParser {

  protected AbstractTextParser next;

  public void setNext(AbstractTextParser parser) {
    this.next = parser;
  }

  abstract public TextPart parse(String text);


}
