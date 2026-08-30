package io.github.lolens.inno2.parser;

import io.github.lolens.inno2.entity.TextPart;
import io.github.lolens.inno2.entity.TextPartType;
import io.github.lolens.inno2.entity.impl.TextPartImpl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Next is {@link WordTextParser}
 */
public class LexemeTextParser extends AbstractTextParser {

  private static final Pattern TOKEN_PATTERN =
      Pattern.compile("(?U)(?<word>\\w+)|(?U)(?<separator>\\W+)");

  // lexeme -> word | separator-characters
  @Override
  public TextPart parse(String text) {

    TextPart part = new TextPartImpl(TextPartType.LEXEME, text);;

    Matcher matcher = TOKEN_PATTERN.matcher(text);
    while (matcher.find()) {
      String word = matcher.group("word");
      if (word != null) {
        var child = next.parse(word);
        part.addChild(child);
      } else {
        String separator = matcher.group("separator");
        for (char ch : separator.toCharArray()) {
          TextPart characterTextPart = new TextPartImpl(TextPartType.CHARACTER, ch);
          part.addChild(characterTextPart);
        }
      }
    }

    return part;
  }
}