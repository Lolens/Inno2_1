package io.github.lolens.inno2.visitor.impl;

import io.github.lolens.inno2.entity.TextPart;
import io.github.lolens.inno2.entity.impl.*;
import io.github.lolens.inno2.visitor.TextPartVisitor;

import java.util.List;

public class RestoreVisitor implements TextPartVisitor<String> {
  @Override
  public String visitText(TextPart part) {
    return joinChildren(part, "\n"); // paragraph join
  }

  @Override
  public String visitParagraph(TextPart part) {
    return joinChildren(part, " "); // sentence join
  }

  @Override
  public String visitSentence(TextPart part) {
    return joinChildren(part, " "); // word join
  }

  @Override
  public String visitLexeme(TextPart part) {
    return concatChildren(part);
  }

  @Override
  public String visitWord(TextPart part) {
    return concatChildren(part);
  }

  @Override
  public String visitCharacter(TextPart part) {
    return part.getContents();
  }

  private String concatChildren(TextPart part) {
    StringBuilder sb = new StringBuilder();
    for (TextPart child : part.getChildren()) {
      sb.append(child.accept(this));
    }
    return sb.toString();
  }

  private String joinChildren(TextPart part, String separator) {
    List<TextPart> children = part.getChildren();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < children.size(); i++) {
      sb.append(children.get(i).accept(this));
      if (i < children.size() - 1) sb.append(separator);
    }
    return sb.toString();
  }
}
