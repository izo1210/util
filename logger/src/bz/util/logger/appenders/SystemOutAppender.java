package bz.util.logger.appenders;

import bz.util.logger.model.Appender;

public class SystemOutAppender implements Appender
{
  @Override
  public void append(Object formatterRecord)
  {
    System.out.println(formatterRecord);
  }

}
