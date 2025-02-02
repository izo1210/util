package bz.util.strings;

import static org.junit.Assert.*;
import org.junit.Test;

public class CaseTest
{

  public CaseTest()
  {
  }

  @Test
  public void testConvert_4args()
  {
    assertEquals(
        "SPLIT/this/text",
        Case.convert("Split This Text", String::toUpperCase, "/",  String::toLowerCase)
    );
  }

  @Test
  public void testConvert_3args()
  {
    assertEquals(
        "'Split' 'This' 'Text'",
        Case.convert("Split This Text", word->"'"+word+"'", " ")
    );
  }

  @Test
  public void testSplitWords()
  {
    assertArrayEquals(
        new String[]{"Split", "this", "TEXT"},
        Case.splitWords("Split this TEXT")
    );
  }

  @Test
  public void testMap()
  {
    assertArrayEquals(
        new String[]{"SPLIT", "this", "text"},
        Case.map(new String[]{"Split", "This", "Text"}, String::toUpperCase, String::toLowerCase)
    );
  }

  @Test
  public void testCapitalize()
  {
    assertEquals("Abc", Case.capitalize("abc"));
    assertEquals("Abc", Case.capitalize("Abc"));
    assertEquals("Abc", Case.capitalize("aBC"));
    assertEquals("Abc", Case.capitalize("ABC"));
  }

  @Test
  public void testCamelCase()
  {
    assertEquals("splitThisText", Case.camelCase("Split This Text"));
  }

  @Test
  public void testPascalCase()
  {
    assertEquals("SplitThisText", Case.PascalCase("Split This Text"));
  }

  @Test
  public void testSnake_case()
  {
    assertEquals("split_this_text", Case.snake_case("Split This Text"));
  }

  @Test
  public void testSNAKE_CASE()
  {
    assertEquals("SPLIT_THIS_TEXT", Case.SNAKE_CASE("Split This Text"));
  }

  @Test
  public void testKebab_case()
  {
    assertEquals("split-this-text", Case.kebab_case("Split This Text"));
  }

  @Test
  public void testTrain_Case()
  {
    assertEquals("Split-This-Text", Case.Train_Case("Split This Text"));
  }

  @Test
  public void testDotCase()
  {
    assertEquals("split.this.text", Case.dotCase("Split This Text"));
  }

}
