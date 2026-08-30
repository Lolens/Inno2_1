package io.github.lolens.inno2.visitor;

import io.github.lolens.inno2.entity.TextPart;
import io.github.lolens.inno2.entity.impl.*;

public interface TextPartVisitor<R> {

  R visitText(TextPart part);
  R visitParagraph(TextPart part);
  R visitSentence(TextPart part);
  R visitLexeme(TextPart part);
  R visitWord(TextPart part);
  R visitCharacter(TextPart part);

}
