package bz.util.strings;

import java.util.function.Consumer;
import java.util.function.Function;

public class Cut 
{
  public static Cut string(String input)
  {
    return new Cut(input);
  }

  private String remaining;
  private String removed;

  public Cut(String input)
  {
    remaining=input;
  }

  @Override
  public String toString()
  {
    return remaining;
  }

  public enum Part { before, after }
  
  public enum The { beginningOf, endOf }

  public Cut remove(Part removedPart, The theEdgeOfSubstring, String substring, int occurrence, int offset)
  {
    if(substring==null)
    {
      return removeNothing();
    }

    int startOfSubstring=startOfSubstring(substring, occurrence);
    if(startOfSubstring==-1)
    {
      return removeNothing();
    }
    
    int indexOfCut=startOfSubstring+offset;
    if(theEdgeOfSubstring==The.endOf)
    {
      indexOfCut+=substring.length();
    }

    if(removedPart==Part.before)
    {
      removed=remaining.substring(0, indexOfCut);
      remaining=remaining.substring(indexOfCut);
    }
    else //if(removedPart==Remove.after)
    {
      removed=remaining.substring(indexOfCut);
      remaining=remaining.substring(0, indexOfCut);
    }

    return this;
  }

  public Cut remove(Part removePart, The theEdgeOfSubstring, String substring)
  {
    return remove(removePart, theEdgeOfSubstring, substring, 1, 0);
  }

  private int startOfSubstring(String substring, int occurrence)
  {
    if(substring.isEmpty())
    {
      return 0;
    }
    
    int index=-1;
    for(; occurrence>0; occurrence--)
    {
      index=remaining.indexOf(substring, index+1);
      if(index==-1) break;
    }
    return index;
  }

  private Cut removeNothing()
  {
    removed=null;
    return this;
  }

  public String removed()
  {
    return removed;
  }

  public Cut forRemaining(Consumer<String> operation)
  {
    operation.accept(remaining);
    return this;
  }

  public Cut forRemoved(Consumer<String> operation)
  {
    operation.accept(removed);
    return this;
  }

  public Cut setRemaining(Function<String, String> operation)
  {
    remaining=operation.apply(remaining);
    return removeNothing();
  }

}
