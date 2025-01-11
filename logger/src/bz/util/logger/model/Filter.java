package bz.util.logger.model;

public interface Filter
{
  boolean evaluate(Record record);
}
