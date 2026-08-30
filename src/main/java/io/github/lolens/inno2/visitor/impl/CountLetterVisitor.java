package io.github.lolens.inno2.visitor.impl;

import io.github.lolens.inno2.entity.TextPart;
import io.github.lolens.inno2.entity.impl.*;
import io.github.lolens.inno2.visitor.TextPartVisitor;

import java.util.List;

public class CountLetterVisitor implements TextPartVisitor<Integer> {

  private Character letterToFind;

  public CountLetterVisitor() {

  }

  public CountLetterVisitor(char letterToFind) {
    this.letterToFind = letterToFind;
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
    char ch = part.getContents().charAt(0);
    if (!Character.isLetter(ch)) return 0; // not a letter

    if (letterToFind != null) {
      return letterToFind == ch ? 1 : 0;
    }

    return 1;
  }

  private Integer visitChildren(TextPart part) {
    int accumulator = 0;
    List<TextPart> children = part.getChildren();
    for (TextPart child : children) {
      accumulator += child.accept(this);
    }
    return accumulator;
  }
}
