package io.github.lolens.inno2.parser;

import io.github.lolens.inno2.entity.TextPart;
import io.github.lolens.inno2.entity.TextPartType;
import io.github.lolens.inno2.entity.impl.TextPartImpl;

/**
 * Next is {@link SentenceTextParser}
 */
public class ParagraphTextParser extends AbstractTextParser {

  public static final String PARAGRAPH_SPLIT_REGEX = "(?<=[.?!])\\s+";

  @Override
  public TextPart parse(String text) {

    text = text.strip();
    String[] paragraphs = text.split(PARAGRAPH_SPLIT_REGEX);
    TextPart part = new TextPartImpl(TextPartType.PARAGRAPH, text);

    for (String paragraph : paragraphs) {
      var child = next.parse(paragraph);
      part.addChild(child);
    }

    return part;
  }
}
