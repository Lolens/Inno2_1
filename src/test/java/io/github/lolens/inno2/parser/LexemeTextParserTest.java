package io.github.lolens.inno2.parser;

import io.github.lolens.inno2.entity.TextPart;
import io.github.lolens.inno2.entity.TextPartType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LexemeTextParserTest {

  private LexemeTextParser lexemeParser;

  @BeforeEach
  void setUp() {
    lexemeParser = new LexemeTextParser();
    lexemeParser.setNext(new WordTextParser());
  }

  @Test
  void parseShouldCreateSingleWordChildForPlainWord() {
    TextPart lexeme = lexemeParser.parse("hello");

    assertEquals(TextPartType.LEXEME, lexeme.getType());
    assertEquals(1, lexeme.getChildren().size());
    assertEquals(TextPartType.WORD, lexeme.getChildren().getFirst().getType());
    assertEquals("hello", lexeme.getChildren().getFirst().getContents());
  }

  @Test
  void parseShouldPreserveTrailingPunctuationAsCharacter() {
    TextPart lexeme = lexemeParser.parse("word,");

    assertEquals(2, lexeme.getChildren().size());
    assertEquals(TextPartType.WORD, lexeme.getChildren().get(0).getType());
    assertEquals(TextPartType.CHARACTER, lexeme.getChildren().get(1).getType());
    assertEquals(",", lexeme.getChildren().get(1).getContents());
  }

  @Test
  void parseShouldPreserveInternalApostropheBetweenWordParts() {
    TextPart lexeme = lexemeParser.parse("don't");

    assertEquals(3, lexeme.getChildren().size());
    assertEquals("don", lexeme.getChildren().get(0).getContents());
    assertEquals(TextPartType.CHARACTER, lexeme.getChildren().get(1).getType());
    assertEquals("'", lexeme.getChildren().get(1).getContents());
    assertEquals("t", lexeme.getChildren().get(2).getContents());
  }

  @Test
  void parseShouldSplitMultiCharacterSeparatorIntoIndividualCharacters() {
    TextPart lexeme = lexemeParser.parse("word...");

    assertEquals(4, lexeme.getChildren().size());
    for (int i = 1; i <= 3; i++) {
      assertEquals(TextPartType.CHARACTER, lexeme.getChildren().get(i).getType());
      assertEquals(".", lexeme.getChildren().get(i).getContents());
    }
  }

  @Test
  void parseShouldHandleLeadingPunctuationBeforeWord() {
    TextPart lexeme = lexemeParser.parse("(word)");

    assertEquals(3, lexeme.getChildren().size());
    assertEquals(TextPartType.CHARACTER, lexeme.getChildren().get(0).getType());
    assertEquals("(", lexeme.getChildren().get(0).getContents());
    assertEquals(TextPartType.WORD, lexeme.getChildren().get(1).getType());
    assertEquals(")", lexeme.getChildren().get(2).getContents());
  }
}