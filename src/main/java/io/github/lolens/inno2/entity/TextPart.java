package io.github.lolens.inno2.entity;

import io.github.lolens.inno2.visitor.TextPartVisitor;

import java.util.List;

public interface TextPart {

  TextPartType getType();

  String getContents();
  void setContents(String contents);

  void addChild(TextPart child);
  void removeChild(TextPart child);
  List<TextPart> getChildren();

  TextPart getParent();
  void setParent(TextPart parent);

  <R> R accept(TextPartVisitor<R> visitor);

  default boolean isText() {
    return getType() == TextPartType.TEXT;
  }

  default boolean isParagraph() {
    return getType() == TextPartType.PARAGRAPH;
  }

  default boolean isSentence() {
    return getType() == TextPartType.SENTENCE;
  }

  default boolean isLexeme() {
    return getType() == TextPartType.LEXEME;
  }

  default boolean isWord() {
    return getType() == TextPartType.WORD;
  }

  default boolean isCharacter() {
    return getType() == TextPartType.CHARACTER;
  }

  default boolean isDeeperThan(TextPartType type) {
    return getType().getDepth() > type.getDepth();
  }

}
