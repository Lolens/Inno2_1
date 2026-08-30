package io.github.lolens.inno2.parser;

import io.github.lolens.inno2.entity.TextPart;
import io.github.lolens.inno2.entity.TextPartType;
import io.github.lolens.inno2.entity.impl.TextPartImpl;

/**
 * Next is {@link LexemeTextParser}
 */
public class SentenceTextParser extends AbstractTextParser{

  public static final String SENTENCE_SPLIT_REGEX = "\\s+";

  @Override
  public TextPart parse(String text) {

    text = text.strip();
    String[] paragraphs = text.split(SENTENCE_SPLIT_REGEX);
    TextPart part = new TextPartImpl(TextPartType.SENTENCE, text);

    for (String paragraph : paragraphs) {
      var child = next.parse(paragraph);
      part.addChild(child);
    }

    return part; // lexeme
  }
}
