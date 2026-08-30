package io.github.lolens.inno2.service;

import io.github.lolens.inno2.entity.TextPart;

import java.util.List;

public interface TextService {

  TextPart parse(String text);

  String restore(TextPart part);

  int countLetters(TextPart part);

  int countLetters(TextPart part, char letter);

  int countChararacters(TextPart part, char ch);

  int countChararacters(TextPart part);

  int countSentencesContainingSameWord(TextPart part);

  void sortSentencesByCharCount(TextPart part);

  void swapFirstAndLastLexemesInSentence(TextPart part);



}
