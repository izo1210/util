package bz.util.java;

import java.util.Arrays;
import java.util.function.Consumer;

public abstract class With
{
  public static <T> T object(T object, Consumer<T>... functionsWithObject)
  {
    if(object!=null)
    {
      Arrays.stream(functionsWithObject).forEach(function->function.accept(object));
    }
    return object;
  }
}

