package io.github.lolens.inno2.visitor.impl;

import io.github.lolens.inno2.entity.TextPart;
import io.github.lolens.inno2.entity.impl.*;
import io.github.lolens.inno2.visitor.TextPartVisitor;

import java.util.*;

public class CollectUniqueWordsPerSentenceVisitor implements TextPartVisitor<List<Set<String>>> {

  @Override
  public List<Set<String>> visitText(TextPart part) {
    return collectFromChildren(part);
  }

  @Override
  public List<Set<String>> visitParagraph(TextPart part) {
    return collectFromChildren(part);
  }

  @Override
  public List<Set<String>> visitSentence(TextPart part) {
    Set<String> uniqueWords = new HashSet<>();
    collectWords(part, uniqueWords);
    return List.of(uniqueWords);
  }

  @Override
  public List<Set<String>> visitLexeme(TextPart part) {
    return List.of();
  }

  @Override
  public List<Set<String>> visitWord(TextPart part) {
    return List.of();
  }

  @Override
  public List<Set<String>> visitCharacter(TextPart part) {
    return List.of();
  }

  private List<Set<String>> collectFromChildren(TextPart part) {
    List<Set<String>> result = new ArrayList<>();
    for (TextPart child : part.getChildren()) {
      result.addAll(child.accept(this));
    }
    return result;
  }

  private void collectWords(TextPart part, Set<String> accumulator) {
    if (part.isWord()) {
      accumulator.add(part.getContents().toLowerCase());
      return;
    }
    for (TextPart child : part.getChildren()) {
      collectWords(child, accumulator);
    }
  }
}