package io.github.lolens.inno2.visitor.impl;

import io.github.lolens.inno2.entity.TextPart;
import io.github.lolens.inno2.entity.impl.*;
import io.github.lolens.inno2.visitor.TextPartVisitor;

import java.util.List;

public class CountCharactersVisitor implements TextPartVisitor<Integer> {

  Character charToFind;

  public CountCharactersVisitor() {

  }

  public CountCharactersVisitor(char charToFind) {
    this.charToFind = charToFind;
  }

  @Override
  public Integer visitText(TextPart part) {
    return visitChildren(part);
  }

  @Override
  public Integer visitParagraph(TextPart part) {
    return visitChildren(part);
  }

  @Override
  public Integer visitSentence(TextPart part) {
    return visitChildren(part);
  }

  @Override
  public Integer visitLexeme(TextPart part) {
    return visitChildren(part);
  }

  @Override
  public Integer visitWord(TextPart part) {
    return visitChildren(part);
  }

  @Override
  public Integer visitCharacter(TextPart part) {
    if (charToFind != null) {
      return charToFind == part.getContents().charAt(0) ? 1 : 0;
    }

    return 1;
  }

  private Integer visitChildren(TextPart part) {
    int accumulator = 0;
    for (TextPart child : part.getChildren()) {
      accumulator += child.accept(this);
    }
    return accumulator;
  }
}
