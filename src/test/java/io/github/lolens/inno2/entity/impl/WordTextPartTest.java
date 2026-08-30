package io.github.lolens.inno2.entity.impl;

import io.github.lolens.inno2.entity.TextPart;
import io.github.lolens.inno2.entity.TextPartType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WordTextPartTest {

  @Test
  void addChildShouldSetParentReference() {
    TextPart word = new WordTextPart("hi");
    TextPart ch = new CharacterTextPart("h");

    word.addChild(ch);

    assertSame(word, ch.getParent());
    assertTrue(word.getChildren().contains(ch));
  }

  @Test
  void removeChildShouldDetachChildFromChildrenList() {
    TextPart word = new WordTextPart("hi");
    TextPart ch1 = new CharacterTextPart("h");
    TextPart ch2 = new CharacterTextPart("i");
    word.addChild(ch1);
    word.addChild(ch2);

    word.removeChild(ch1);

    assertEquals(1, word.getChildren().size());
    assertSame(ch2, word.getChildren().getFirst());
  }

  @Test
  void typeCheckDefaultMethodsShouldReflectActualType() {
    TextPart character = new CharacterTextPart("x");

    assertTrue(character.isCharacter());
    assertFalse(character.isWord());
    assertFalse(character.isLexeme());
  }

  @Test
  void isDeeperThanShouldCompareByDepth() {
    TextPart character = new CharacterTextPart("x");

    assertTrue(character.isDeeperThan(TextPartType.WORD));
    assertFalse(character.isDeeperThan(TextPartType.CHARACTER));
  }

  @Test
  void asCharShouldReturnFirstCharacterOfContents() {
    CharacterTextPart part = new CharacterTextPart("Z");

    assertEquals('Z', part.asChar());
  }
}