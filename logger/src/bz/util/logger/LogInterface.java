package bz.util.logger;

import bz.util.logger.model.Event;
import bz.util.logger.model.Record;

/**
 * Implement this interface where you want to use methods.
 */
public interface LogInterface
{
  public static final int ALL=Integer.MIN_VALUE;
  public static final int VERBOSE=-20;
  public static final int DEBUG=-10;
  public static final int INFO=0;
  public static final int WARNING=10;
  public static final int ERROR=20;
  public static final int NOTHING=Integer.MAX_VALUE;

  default void log(Object... keysAndValuesAndMaybeLevel)
  {
    event(Event.LOG, keysAndValuesAndMaybeLevel);
  }

  default void logStart(Object... keysAndValuesAndMaybeLevel)
  {
    event(Event.START, keysAndValuesAndMaybeLevel);
  }

  default void logEnd(Object... keysAndValuesAndMaybeLevel)
  {
    event(Event.END, keysAndValuesAndMaybeLevel);
  }

  default void event(Event event, Object[] keysAndValuesAndMaybeLevel)
  {
    Record record=new Record(this, event, keysAndValuesAndMaybeLevel);
    logger().log(record);
  }

  default BzLogger logger()
  {
    return BzLogger.defaultLogger;
  }

  default String objectId()
  {
    return "0x"+Integer.toHexString(System.identityHashCode(this));
  }

  default AutoCloseable logBlock(Object... keysAndValuesAndMaybeLevel)
  {
    event(Event.START, keysAndValuesAndMaybeLevel);

    return (AutoCloseable)(()->{
      event(Event.END, keysAndValuesAndMaybeLevel);
    });
  }
}
