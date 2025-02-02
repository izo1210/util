package bz.util.strings;

import bz.util.strings.Cut.Part;
import bz.util.strings.Cut.The;
import static org.junit.Assert.*;
import org.junit.Test;

public class CutTest
{
  private final Cut cut;

  public CutTest()
  {
    cut=Cut.string("abbc");
  }

  void expected(String remaining, String removed)
  {
    assertEquals(cut.toString(), remaining);
    assertEquals(cut.removed(), removed);
  }

  @Test
  public void before_beginningOf_a()
  {
    cut.remove(Part.before, The.beginningOf, "a");
    expected("abbc", "");
  }

  @Test
  public void before_endOf_a()
  {
    cut.remove(Part.before, The.endOf, "a");
    expected("bbc", "a");
  }

  @Test
  public void after_beginningOf_a()
  {
    cut.remove(Part.after, The.beginningOf, "a");
    expected("", "abbc");
  }

  @Test
  public void after_endOf_a()
  {
    cut.remove(Part.after, The.endOf, "a");
    expected("a", "bbc");
  }

  @Test
  public void before_beginningOf_c()
  {
    cut.remove(Part.before, The.beginningOf, "c");
    expected("c", "abb");
  }

  @Test
  public void before_endOf_c()
  {
    cut.remove(Part.before, The.endOf, "c");
    expected("", "abbc");
  }

  @Test
  public void after_beginningOf_c()
  {
    cut.remove(Part.after, The.beginningOf, "c");
    expected("abb", "c");
  }

  @Test
  public void after_endOf_c()
  {
    cut.remove(Part.after, The.endOf, "c");
    expected("abbc", "");
  }

  @Test
  public void before_beginningOf_b()
  {
    cut.remove(Part.before, The.beginningOf, "b");
    expected("bbc", "a");
  }

  @Test
  public void before_endOf_b()
  {
    cut.remove(Part.before, The.endOf, "b");
    expected("bc", "ab");
  }

  @Test
  public void after_beginningOf_b()
  {
    cut.remove(Part.after, The.beginningOf, "b");
    expected("a", "bbc");
  }

  @Test
  public void after_endOf_b()
  {
    cut.remove(Part.after, The.endOf, "b", 2, 0);
    expected("abb", "c");
  }

  @Test
  public void before_beginningOf_b_2()
  {
    cut.remove(Part.before, The.beginningOf, "b", 2, 0);
    expected("bc", "ab");
  }

  @Test
  public void before_endOf_b_2()
  {
    cut.remove(Part.before, The.endOf, "b", 2, 0);
    expected("c", "abb");
  }

  @Test
  public void after_beginningOf_b_2()
  {
    cut.remove(Part.after, The.beginningOf, "b", 2, 0);
    expected("ab", "bc");
  }

  @Test
  public void after_endOf_b_2()
  {
    cut.remove(Part.after, The.endOf, "b", 2, 0);
    expected("abb", "c");
  }

  @Test
  public void before_beginningOf_b_1_1()
  {
    cut.remove(Part.before, The.beginningOf, "b", 1, 1);
    expected("bc", "ab");
  }

  @Test
  public void before_endOf_b_1_1()
  {
    cut.remove(Part.before, The.endOf, "b", 1, 1);
    expected("c", "abb");
  }

  @Test
  public void after_beginningOf_b_1_1()
  {
    cut.remove(Part.after, The.beginningOf, "b", 1, 1);
    expected("ab", "bc");
  }

  @Test
  public void after_endOf_b_1_1()
  {
    cut.remove(Part.after, The.endOf, "b", 1, 1);
    expected("abb", "c");
  }

  @Test
  public void before_beginningOf_c_1_minus1()
  {
    cut.remove(Part.before, The.beginningOf, "c", 1, -1);
    expected("bc", "ab");
  }

  @Test
  public void before_endOf_c_1_minus1()
  {
    cut.remove(Part.before, The.endOf, "c", 1, -1);
    expected("c", "abb");
  }

  @Test
  public void after_beginningOf_c_1_minus1()
  {
    cut.remove(Part.after, The.beginningOf, "c", 1, -1);
    expected("ab", "bc");
  }

  @Test
  public void after_endOf_c_1_minus1()
  {
    cut.remove(Part.after, The.endOf, "c", 1, -1);
    expected("abb", "c");
  }

  @Test
  public void absolute()
  {
    cut.remove(Part.after, The.beginningOf, "", 1, 1);
    expected("a", "bbc");
  }

  @Test
  public void substringIsNull()
  {
    cut.remove(Part.after, The.beginningOf, null);
    expected("abbc", null);
  }

  @Test
  public void substringNotFound()
  {
    cut.remove(Part.after, The.beginningOf, "d");
    expected("abbc", null);
  }

  @Test
  public void substringFoundButRemovedPartIsEmpty()
  {
    cut.remove(Part.after, The.endOf, "c");
    expected("abbc", "");
  }

  @Test
  public void cutBeforeTheBeginning()
  {
    assertThrows(IndexOutOfBoundsException.class, ()->cut.remove(Part.before, The.beginningOf, "a", 1, -1));
  }

  @Test
  public void cutAfterTheEnd()
  {
    assertThrows(IndexOutOfBoundsException.class, ()->cut.remove(Part.after, The.endOf, "c", 1, 1));
  }

  @Test
  public void testForRemaining()
  {
    cut.remove(Part.after, The.endOf, "b")
        .forRemaining(remaining->assertEquals("ab", remaining))
        .remove(Part.after, The.endOf, "a");
    expected("a", "b");
  }

  @Test
  public void testForRemoved()
  {
    cut.remove(Part.after, The.endOf, "b")
        .forRemoved(removed->assertEquals("bc", removed))
        .remove(Part.after, The.endOf, "a");
    expected("a", "b");
  }

}
