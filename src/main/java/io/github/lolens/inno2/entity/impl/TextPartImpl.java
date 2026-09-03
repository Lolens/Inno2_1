package io.github.lolens.inno2.entity.impl;

import io.github.lolens.inno2.entity.TextPart;
import io.github.lolens.inno2.entity.TextPartType;
import io.github.lolens.inno2.visitor.TextPartVisitor;

import java.util.ArrayList;
import java.util.List;

public class TextPartImpl implements TextPart {

  // NOTE: At the end of this file you can see toString() tree output of the one of the example paragraphs.

  private final TextPartType type;
  private String contents;

  private final List<TextPart> children = new ArrayList<>();
  private TextPart parent;

  public TextPartImpl(TextPartType type, String contents) {
    this.type = type;
    this.contents = contents;
  }

  public TextPartImpl(TextPartType type, char ch) {
    this.type = type;
    this.contents = String.valueOf(ch);
  }

  public TextPartImpl(TextPartType type) {
    this.type = type;
  }

  @Override
  public TextPartType getType() {
    return this.type;
  }

  @Override
  public String getContents() {
    return this.contents;
  }

  @Override
  public void setContents(String contents) {
    this.contents = contents;
  }

  @Override
  public void addChild(TextPart child) {
    child.setParent(this);
    children.add(child);
  }

  @Override
  public void removeChild(TextPart child) {
    children.remove(child);
  }

  @Override
  public List<TextPart> getChildren() {
    return children;
  }

  @Override
  public TextPart getParent() {
    return parent;
  }

  @Override
  public void setParent(TextPart parent) {
    this.parent = parent;
  }

  @Override
  public <R> R accept(TextPartVisitor<R> visitor) {
    return switch (type) {
      case TEXT -> visitor.visitText(this);
      case PARAGRAPH -> visitor.visitParagraph(this);
      case SENTENCE -> visitor.visitSentence(this);
      case LEXEME -> visitor.visitLexeme(this);
      case WORD -> visitor.visitWord(this);
      case CHARACTER -> visitor.visitCharacter(this);
    };
  }

  @Override
  public String toString() {
    return buildTreeString();
  }

  public String buildTreeString() {
    return getAsStringTree(baseIndentFor(type));
  }

  private String getAsStringTree(int indent) {
    StringBuilder sb = new StringBuilder();
    String indentStr = "  ".repeat(indent);

    sb.append(indentStr)
        .append(type)
        .append(": ")
        .append("'").append(contents).append("'");

    if (!children.isEmpty()) {
      sb.append(" {\n");
      for (TextPart child : children) {
        if (child instanceof TextPartImpl impl) {
          sb.append(impl.getAsStringTree(indent + 1));
        } else {
          sb.append("  ".repeat(indent + 1))
              .append(child)
              .append("\n");
        }
      }
      sb.append(indentStr).append("}");
    } else {
      sb.append(" [LEAF]");
    }
    sb.append("\n");

    return sb.toString();
  }

  private static int baseIndentFor(TextPartType type) {
    return switch (type) {
      case TEXT -> 0;
      case PARAGRAPH -> 1;
      case SENTENCE -> 2;
      case LEXEME -> 3;
      case WORD -> 4;
      case CHARACTER -> 5;
    };
  }

  /*

  Every level of the composite is a self-sufficient object,
  that can be represented as text even if every other child is stripped away.

  This way every part of the composite can be represented as some meaningful String
  and also may be a part of a greater TextPart

  PARAGRAPH: 'It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.' {
    SENTENCE: 'It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.' {
      LEXEME: 'It' {
        WORD: 'It' {
          CHARACTER: 'I' [LEAF]
          CHARACTER: 't' [LEAF]
        }
      }
      LEXEME: 'has' {
        WORD: 'has' {
          CHARACTER: 'h' [LEAF]
          CHARACTER: 'a' [LEAF]
          CHARACTER: 's' [LEAF]
        }
      }
      LEXEME: 'survived' {
        WORD: 'survived' {
          CHARACTER: 's' [LEAF]
          CHARACTER: 'u' [LEAF]
          CHARACTER: 'r' [LEAF]
          CHARACTER: 'v' [LEAF]
          CHARACTER: 'i' [LEAF]
          CHARACTER: 'v' [LEAF]
          CHARACTER: 'e' [LEAF]
          CHARACTER: 'd' [LEAF]
        }
      }
      LEXEME: 'not' {
        WORD: 'not' {
          CHARACTER: 'n' [LEAF]
          CHARACTER: 'o' [LEAF]
          CHARACTER: 't' [LEAF]
        }
      }
      LEXEME: 'only' {
        WORD: 'only' {
          CHARACTER: 'o' [LEAF]
          CHARACTER: 'n' [LEAF]
          CHARACTER: 'l' [LEAF]
          CHARACTER: 'y' [LEAF]
        }
      }
      LEXEME: 'five' {
        WORD: 'five' {
          CHARACTER: 'f' [LEAF]
          CHARACTER: 'i' [LEAF]
          CHARACTER: 'v' [LEAF]
          CHARACTER: 'e' [LEAF]
        }
      }
      LEXEME: 'centuries,' {
        WORD: 'centuries' {
          CHARACTER: 'c' [LEAF]
          CHARACTER: 'e' [LEAF]
          CHARACTER: 'n' [LEAF]
          CHARACTER: 't' [LEAF]
          CHARACTER: 'u' [LEAF]
          CHARACTER: 'r' [LEAF]
          CHARACTER: 'i' [LEAF]
          CHARACTER: 'e' [LEAF]
          CHARACTER: 's' [LEAF]
        }
        CHARACTER: ',' [LEAF]
      }
      LEXEME: 'but' {
        WORD: 'but' {
          CHARACTER: 'b' [LEAF]
          CHARACTER: 'u' [LEAF]
          CHARACTER: 't' [LEAF]
        }
      }
      LEXEME: 'also' {
        WORD: 'also' {
          CHARACTER: 'a' [LEAF]
          CHARACTER: 'l' [LEAF]
          CHARACTER: 's' [LEAF]
          CHARACTER: 'o' [LEAF]
        }
      }
      LEXEME: 'the' {
        WORD: 'the' {
          CHARACTER: 't' [LEAF]
          CHARACTER: 'h' [LEAF]
          CHARACTER: 'e' [LEAF]
        }
      }
      LEXEME: 'leap' {
        WORD: 'leap' {
          CHARACTER: 'l' [LEAF]
          CHARACTER: 'e' [LEAF]
          CHARACTER: 'a' [LEAF]
          CHARACTER: 'p' [LEAF]
        }
      }
      LEXEME: 'into' {
        WORD: 'into' {
          CHARACTER: 'i' [LEAF]
          CHARACTER: 'n' [LEAF]
          CHARACTER: 't' [LEAF]
          CHARACTER: 'o' [LEAF]
        }
      }
      LEXEME: 'electronic' {
        WORD: 'electronic' {
          CHARACTER: 'e' [LEAF]
          CHARACTER: 'l' [LEAF]
          CHARACTER: 'e' [LEAF]
          CHARACTER: 'c' [LEAF]
          CHARACTER: 't' [LEAF]
          CHARACTER: 'r' [LEAF]
          CHARACTER: 'o' [LEAF]
          CHARACTER: 'n' [LEAF]
          CHARACTER: 'i' [LEAF]
          CHARACTER: 'c' [LEAF]
        }
      }
      LEXEME: 'typesetting,' {
        WORD: 'typesetting' {
          CHARACTER: 't' [LEAF]
          CHARACTER: 'y' [LEAF]
          CHARACTER: 'p' [LEAF]
          CHARACTER: 'e' [LEAF]
          CHARACTER: 's' [LEAF]
          CHARACTER: 'e' [LEAF]
          CHARACTER: 't' [LEAF]
          CHARACTER: 't' [LEAF]
          CHARACTER: 'i' [LEAF]
          CHARACTER: 'n' [LEAF]
          CHARACTER: 'g' [LEAF]
        }
        CHARACTER: ',' [LEAF]
      }
      LEXEME: 'remaining' {
        WORD: 'remaining' {
          CHARACTER: 'r' [LEAF]
          CHARACTER: 'e' [LEAF]
          CHARACTER: 'm' [LEAF]
          CHARACTER: 'a' [LEAF]
          CHARACTER: 'i' [LEAF]
          CHARACTER: 'n' [LEAF]
          CHARACTER: 'i' [LEAF]
          CHARACTER: 'n' [LEAF]
          CHARACTER: 'g' [LEAF]
        }
      }
      LEXEME: 'essentially' {
        WORD: 'essentially' {
          CHARACTER: 'e' [LEAF]
          CHARACTER: 's' [LEAF]
          CHARACTER: 's' [LEAF]
          CHARACTER: 'e' [LEAF]
          CHARACTER: 'n' [LEAF]
          CHARACTER: 't' [LEAF]
          CHARACTER: 'i' [LEAF]
          CHARACTER: 'a' [LEAF]
          CHARACTER: 'l' [LEAF]
          CHARACTER: 'l' [LEAF]
          CHARACTER: 'y' [LEAF]
        }
      }
      LEXEME: 'unchanged.' {
        WORD: 'unchanged' {
          CHARACTER: 'u' [LEAF]
          CHARACTER: 'n' [LEAF]
          CHARACTER: 'c' [LEAF]
          CHARACTER: 'h' [LEAF]
          CHARACTER: 'a' [LEAF]
          CHARACTER: 'n' [LEAF]
          CHARACTER: 'g' [LEAF]
          CHARACTER: 'e' [LEAF]
          CHARACTER: 'd' [LEAF]
        }
        CHARACTER: '.' [LEAF]
      }
    }

   */

}