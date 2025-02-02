package bz.util.java;

import java.util.Optional;

public abstract class Cast
{
  public static <T> Optional<T> toOptional(Object object, Class<T> type)
  {
    if(object!=null && type.isAssignableFrom(object.getClass()))
    {
      return Optional.of((T)object);
    }
    return Optional.empty();
  }

}
