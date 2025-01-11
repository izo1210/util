package bz.util.logger.model;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import bz.util.logger.model.Record;

public abstract class Formatter
{
  public DateTimeFormatter timestampFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").withZone(ZoneId.systemDefault());
  public Print print=new Print();

  public abstract Object format(Record record);

}
