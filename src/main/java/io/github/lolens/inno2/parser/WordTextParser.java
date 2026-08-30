package io.github.lolens.inno2.parser;

import io.github.lolens.inno2.entity.TextPart;
import io.github.lolens.inno2.entity.TextPartType;
import io.github.lolens.inno2.entity.impl.TextPartImpl;

public class WordTextParser extends AbstractTextParser{
  @Override
  public TextPart parse(String text) {

    TextPart part = new TextPartImpl(TextPartType.WORD, text);

    for (char ch : text.toCharArray()) {
      TextPart chTextPart = new TextPartImpl(TextPartType.CHARACTER, ch);
      part.addChild(chTextPart);
    }

    // word -> character
    return part;
  }
}
