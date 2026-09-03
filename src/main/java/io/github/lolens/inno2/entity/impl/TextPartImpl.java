package io.github.lolens.inno2.entity.impl;

import io.github.lolens.inno2.entity.TextPart;
import io.github.lolens.inno2.entity.TextPartType;
import io.github.lolens.inno2.visitor.TextPartVisitor;

import java.util.ArrayList;
import java.util.List;

public class TextPartImpl implements TextPart {

  private final TextPartType type;
  private String contents;

  private final List<TextPart> children = new ArrayList<>();
  private TextPart parent;

  public TextPartImpl(TextPartType type, String contents) {
    this.type = type;
    this.contents = contents;
  }

  public TextPartImpl(TextPartType type, char ch) {
    this.type = type;
    this.contents = String.valueOf(ch);
  }

  public TextPartImpl(TextPartType type) {
    this.type = type;
  }

  @Override
  public TextPartType getType() {
    return this.type;
  }

  @Override
  public String getContents() {
    return this.contents;
  }

  @Override
  public void setContents(String contents) {
    this.contents = contents;
  }

  @Override
  public void addChild(TextPart child) {
    child.setParent(this);
    children.add(child);
  }

  @Override
  public void removeChild(TextPart child) {
    children.remove(child);
  }

  @Override
  public List<TextPart> getChildren() {
    return children;
  }

  @Override
  public TextPart getParent() {
    return parent;
  }

  @Override
  public void setParent(TextPart parent) {
    this.parent = parent;
  }

  @Override
  public <R> R accept(TextPartVisitor<R> visitor) {
    return switch (type) {
      case TEXT -> visitor.visitText(this);
      case PARAGRAPH -> visitor.visitParagraph(this);
      case SENTENCE -> visitor.visitSentence(this);
      case LEXEME -> visitor.visitLexeme(this);
      case WORD -> visitor.visitWord(this);
      case CHARACTER -> visitor.visitCharacter(this);
    };
  }

  @Override
  public String toString() {
    return buildTreeString();
  }

  public String buildTreeString() {
    return getAsStringTree(baseIndentFor(type));
  }

  private String getAsStringTree(int indent) {
    StringBuilder sb = new StringBuilder();
    String indentStr = "  ".repeat(indent);

    sb.append(indentStr)
        .append(type)
        .append(": ")
        .append("'").append(contents).append("'");

    if (!children.isEmpty()) {
      sb.append(" {\n");
      for (TextPart child : children) {
        if (child instanceof TextPartImpl impl) {
          sb.append(impl.getAsStringTree(indent + 1));
        } else {
          sb.append("  ".repeat(indent + 1))
              .append(child)
              .append("\n");
        }
      }
      sb.append(indentStr).append("}");
    } else {
      sb.append(" [LEAF]");
    }
    sb.append("\n");

    return sb.toString();
  }

  private static int baseIndentFor(TextPartType type) {
    return switch (type) {
      case TEXT -> 0;
      case PARAGRAPH -> 1;
      case SENTENCE -> 2;
      case LEXEME -> 3;
      case WORD -> 4;
      case CHARACTER -> 5;
    };
  }
}