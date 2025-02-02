package bz.util.strings;

public class CaseByChatGPT
{

  // Helper to split words and capitalize as needed
  private static String[] splitWords(String input)
  {
    return input.trim().toLowerCase().split("\\s+");
  }

  // Convert to camelCase
  public static String toCamelCase(String input)
  {
    String[] words=splitWords(input);
    StringBuilder result=new StringBuilder(words[0]);
    for(int i=1; i<words.length; i++)
    {
      result.append(capitalize(words[i]));
    }
    return result.toString();
  }

  // Convert to PascalCase
  public static String toPascalCase(String input)
  {
    String[] words=splitWords(input);
    StringBuilder result=new StringBuilder();
    for(String word: words)
    {
      result.append(capitalize(word));
    }
    return result.toString();
  }

  // Convert to snake_case
  public static String toSnakeCase(String input)
  {
    return String.join("_", splitWords(input));
  }

  // Convert to SNAKE_CASE
  public static String toScreamingSnakeCase(String input)
  {
    return String.join("_", splitWords(input)).toUpperCase();
  }

  // Convert to kebab-case
  public static String toKebabCase(String input)
  {
    return String.join("-", splitWords(input));
  }

  // Convert to Train-Case
  public static String toTrainCase(String input)
  {
    String[] words=splitWords(input);
    StringBuilder result=new StringBuilder();
    for(int i=0; i<words.length; i++)
    {
      if(i>0)
      {
        result.append("-");
      }
      result.append(capitalize(words[i]));
    }
    return result.toString();
  }

  // Convert to dot.case
  public static String toDotCase(String input)
  {
    return String.join(".", splitWords(input));
  }

  // Helper to capitalize the first letter of a word
  private static String capitalize(String word)
  {
    if(word==null||word.isEmpty())
    {
      return word;
    }
    return word.substring(0, 1).toUpperCase()+word.substring(1);
  }
  /*
  public static void main(String[] args)
  {
    String input="convert this string";

    System.out.println("camelCase: "+toCamelCase(input));
    System.out.println("PascalCase: "+toPascalCase(input));
    System.out.println("snake_case: "+toSnakeCase(input));
    System.out.println("SNAKE_CASE: "+toScreamingSnakeCase(input));
    System.out.println("kebab-case: "+toKebabCase(input));
    System.out.println("Train-Case: "+toTrainCase(input));
    System.out.println("dot.case: "+toDotCase(input));
  }
  */
}
