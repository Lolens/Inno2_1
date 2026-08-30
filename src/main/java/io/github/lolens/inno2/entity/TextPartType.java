package io.github.lolens.inno2.entity;

public enum TextPartType {
  TEXT(0),
  PARAGRAPH(1),
  SENTENCE(2),
  LEXEME(3),
  WORD(4),
  CHARACTER(5);

  private final int depth;

  TextPartType(int depth) {
    this.depth = depth;
  }

  public int getDepth() {
    return depth;
  }

}
