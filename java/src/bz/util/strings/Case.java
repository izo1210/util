package bz.util.strings;

import java.util.Optional;
import java.util.function.Function;

public abstract class Case
{

  public static String convert(String input, Function<String, String> mapFirstWord, String delimiter, Function<String, String> mapSubsequentWords)
  {
    return Optional.ofNullable(input)
        .map(string->splitWords(string))
        .map(words->map(words, mapFirstWord, mapSubsequentWords))
        .map(words->String.join(delimiter, words))
        .orElse(null);
  }

  public static String convert(String input, Function<String, String> mapAllWords, String delimiter)
  {
    return convert(input, mapAllWords, delimiter, mapAllWords);
  }

  public static String[] map(String[] words, Function<String, String> mapFirstWord, Function<String, String> mapSubsequentWords)
  {
    for(int i=0; i<words.length; i++)
    {
      words[i]=(i==0 ? mapFirstWord.apply(words[i]) : mapSubsequentWords.apply(words[i]));
    }
    return words;
  }

  // Helper to split words and capitalize as needed
  public static String[] splitWords(String input)
  {
    return input.trim().split("\\s+");
  }

  // Helper to capitalize the first letter of a word
  public static String capitalize(String word)
  {
    if(word==null) return null;
    if(word.isEmpty()) return word;
    return word.substring(0, 1).toUpperCase()+word.substring(1).toLowerCase();
  }

  // Convert to camelCase
  public static String camelCase(String input)
  {
    return convert(input, String::toLowerCase, "", Case::capitalize);
  }

  // Convert to PascalCase
  public static String PascalCase(String input)
  {
    return convert(input, Case::capitalize, "", Case::capitalize);
  }

  // Convert to snake_case
  public static String snake_case(String input)
  {
    return convert(input, String::toLowerCase, "_", String::toLowerCase);
  }

  // Convert to SNAKE_CASE
  public static String SNAKE_CASE(String input)
  {
    return convert(input, String::toUpperCase, "_", String::toUpperCase);
  }

  // Convert to kebab-case
  public static String kebab_case(String input)
  {
    return convert(input, String::toLowerCase, "-", String::toLowerCase);
  }

  // Convert to Train-Case
  public static String Train_Case(String input)
  {
    return convert(input, Case::capitalize, "-", Case::capitalize);
  }

  // Convert to dot.case
  public static String dotCase(String input)
  {
    return convert(input, String::toLowerCase, ".", String::toLowerCase);
  }
  /*
  public static void main(String[] args)
  {
    String input="convert this string";

    System.out.println("camelCase: "+camelCase(input));
    System.out.println("PascalCase: "+PascalCase(input));
    System.out.println("snake_case: "+snake_case(input));
    System.out.println("SNAKE_CASE: "+SNAKE_CASE(input));
    System.out.println("kebab-case: "+kebab_case(input));
    System.out.println("Train-Case: "+Train_Case(input));
    System.out.println("dot.case: "+dotCase(input));
  }
  */
}
