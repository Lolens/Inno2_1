package io.github.lolens.inno2.parser;

import io.github.lolens.inno2.entity.TextPart;
import io.github.lolens.inno2.entity.TextPartType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WordTextParserTest {

  private final WordTextParser parser = new WordTextParser();

  @Test
  void parseShouldCreateOneCharacterChildPerLetter() {
    TextPart word = parser.parse("cat");

    assertEquals(TextPartType.WORD, word.getType());
    assertEquals(3, word.getChildren().size());
    assertEquals("c", word.getChildren().get(0).getContents());
    assertEquals("a", word.getChildren().get(1).getContents());
    assertEquals("t", word.getChildren().get(2).getContents());
  }

  @Test
  void parseShouldSetParentOnEachCharacter() {
    TextPart word = parser.parse("hi");

    for (TextPart child : word.getChildren()) {
      assertSame(word, child.getParent());
    }
  }

  @Test
  void parseOfEmptyStringShouldProduceNoChildren() {
    TextPart word = parser.parse("");

    assertTrue(word.getChildren().isEmpty());
  }
}