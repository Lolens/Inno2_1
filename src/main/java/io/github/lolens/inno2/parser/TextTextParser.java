package io.github.lolens.inno2.parser;

import io.github.lolens.inno2.entity.TextPart;
import io.github.lolens.inno2.entity.TextPartType;
import io.github.lolens.inno2.entity.impl.TextPartImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Next is {@link ParagraphTextParser}
 */
public class TextTextParser extends AbstractTextParser{

  static final Logger logger = LoggerFactory.getLogger(TextTextParser.class);

  public static final String TEXT_SPLIT_REGEX = "\\n+\\s+";

  @Override
  public TextPart parse(String text) {

    String[] paragraphs = text.split(TEXT_SPLIT_REGEX);
    TextPart part = new TextPartImpl(TextPartType.TEXT, text);


    for (String paragraph : paragraphs) {
      var child = next.parse(paragraph);
      part.addChild(child);
    }

    return part; // paragraphs
  }
}
