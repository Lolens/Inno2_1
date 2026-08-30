package io.github.lolens.inno2.service.impl;

import io.github.lolens.inno2.entity.TextPart;
import io.github.lolens.inno2.entity.TextPartType;
import io.github.lolens.inno2.parser.*;
import io.github.lolens.inno2.service.TextService;
import io.github.lolens.inno2.visitor.impl.CollectUniqueWordsPerSentenceVisitor;
import io.github.lolens.inno2.visitor.impl.CountCharactersVisitor;
import io.github.lolens.inno2.visitor.impl.CountLetterVisitor;
import io.github.lolens.inno2.visitor.impl.RestoreVisitor;

import java.util.*;

public class TextServiceImpl implements TextService {

  private final TextTextParser parser = makeChain();

  public TextServiceImpl() {

  }

  @Override
  public TextPart parse(String text) {
    return parser.parse(text);
  }

  @Override
  public String restore(TextPart part) {
    return part.accept(new RestoreVisitor());
  }

  @Override
  public int countLetters(TextPart part) {
    return part.accept(new CountLetterVisitor());
  }

  @Override
  public int countLetters(TextPart part, char letter) {
    return part.accept(new CountLetterVisitor(letter));
  }

  @Override
  public int countChararacters(TextPart part) {
    return part.accept(new CountCharactersVisitor());
  }

  @Override
  public int countChararacters(TextPart part, char ch) {
    return part.accept(new CountCharactersVisitor(ch));
  }

  @Override
  public int countSentencesContainingSameWord(TextPart part) {
    List<Set<String>> sentenceWordSets = part.accept(new CollectUniqueWordsPerSentenceVisitor());

    Map<String, Integer> wordToSentenceCount = new HashMap<>();
    for (Set<String> wordsInSentence : sentenceWordSets) {
      for (String word : wordsInSentence) {
        // words are unique per sentence
        wordToSentenceCount.merge(word, 1, Integer::sum);
      }
    }

    return wordToSentenceCount.values().stream()
        .max(Integer::compare)
        .orElse(0);
  }

  @Override
  public void sortSentencesByCharCount(TextPart part) {
    // TextPartType.PARAGRAPH because .getChildren() returns sentences and sorting needs to
    // be done on sentences and not on the detached list that is returned by findAllByType(..).
    List<TextPart> paragraphs = findAllByType(part, TextPartType.PARAGRAPH);

    for (TextPart paragraph : paragraphs) {
      paragraph.getChildren()
          .sort((o1, o2) -> Integer.compare(countChararacters(o1), countChararacters(o2)));
    }
  }

  @Override
  public void swapFirstAndLastLexemesInSentence(TextPart part) {
    List<TextPart> sentences = findAllByType(part, TextPartType.SENTENCE);

    for (TextPart sentence : sentences) {
      swapInTextPart(sentence);
    }
  }

  private static void swapInTextPart(TextPart textPart) {
    List<TextPart> children = textPart.getChildren();
    if (children.size() < 2) return;

    Collections.swap(children, 0, children.size() - 1);
  }

  private <T extends TextPart> List<T> findAllByType(TextPart root, Class<T> clazz) {
    List<T> result = new ArrayList<>();
    findAllByTypeRecursive(root, clazz, result);
    return result;
  }

  private <T extends TextPart> void findAllByTypeRecursive(
      TextPart part,
      Class<T> clazz,
      List<T> result) {

    if (clazz.isInstance(part)) {
      //noinspection unchecked
      result.add((T) part);
    }
    for (TextPart child : part.getChildren()) {
      findAllByTypeRecursive(child, clazz, result);
    }
  }

  private static List<TextPart> findAllByType(TextPart root, TextPartType type) {
    List<TextPart> result = new ArrayList<>();
    findAllByTypeRecursive(root, type, result);
    return result;
  }

  private static void findAllByTypeRecursive(TextPart part, TextPartType type, List<TextPart> result) {
    if (part.getType() == type) {
      result.add(part);
    }
    for (TextPart child : part.getChildren()) {
      findAllByTypeRecursive(child, type, result);
    }
  }

  public static TextTextParser makeChain() {
    io.github.lolens.inno2.parser.TextTextParser textParser = new TextTextParser();
    ParagraphTextParser paragraphTextParser = new ParagraphTextParser();
    SentenceTextParser sentenceTextParser = new SentenceTextParser();
    LexemeTextParser lexemeTextParser = new LexemeTextParser();
    WordTextParser wordTextParser = new WordTextParser();

    textParser.setNext(paragraphTextParser);
    paragraphTextParser.setNext(sentenceTextParser);
    sentenceTextParser.setNext(lexemeTextParser);
    lexemeTextParser.setNext(wordTextParser);
    return textParser;
  }
}
