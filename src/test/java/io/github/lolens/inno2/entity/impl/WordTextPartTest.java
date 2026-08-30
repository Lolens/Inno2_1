package io.github.lolens.inno2.entity.impl;

import io.github.lolens.inno2.entity.TextPart;
import io.github.lolens.inno2.entity.TextPartType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WordTextPartTest {

  @Test
  void addChildShouldSetParentReference() {
    TextPart word = new TextPartImpl(TextPartType.WORD, "hi");
    TextPart ch = new TextPartImpl(TextPartType.CHARACTER, "h");

    word.addChild(ch);

    assertSame(word, ch.getParent());
    assertTrue(word.getChildren().contains(ch));
  }

  @Test
  void removeChildShouldDetachChildFromChildrenList() {
    TextPart word = new TextPartImpl(TextPartType.WORD, "hi");
    TextPart ch1 = new TextPartImpl(TextPartType.CHARACTER, "h");
    TextPart ch2 = new TextPartImpl(TextPartType.CHARACTER, "i");
    word.addChild(ch1);
    word.addChild(ch2);

    word.removeChild(ch1);

    assertEquals(1, word.getChildren().size());
    assertSame(ch2, word.getChildren().getFirst());
  }

  @Test
  void typeCheckDefaultMethodsShouldReflectActualType() {
    TextPart character = new TextPartImpl(TextPartType.CHARACTER, "x");

    assertTrue(character.isCharacter());
    assertFalse(character.isWord());
    assertFalse(character.isLexeme());
  }

  @Test
  void isDeeperThanShouldCompareByDepth() {
    TextPart character = new TextPartImpl(TextPartType.CHARACTER, "x");

    assertTrue(character.isDeeperThan(TextPartType.WORD));
    assertFalse(character.isDeeperThan(TextPartType.CHARACTER));
  }

  @Test
  void asCharShouldReturnFirstCharacterOfContents() {
    TextPartImpl part = new TextPartImpl(TextPartType.CHARACTER, "Z");
    char ch = part.getContents().charAt(0);

    assertEquals('Z', ch);
  }
}