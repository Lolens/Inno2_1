package io.github.lolens.inno2.entity;

public enum TextPartType {
  TEXT,
  PARAGRAPH,
  SENTENCE,
  LEXEME,
  WORD,
  CHARACTER;

  public int getDepth() {
    return this.ordinal();
  }

}
