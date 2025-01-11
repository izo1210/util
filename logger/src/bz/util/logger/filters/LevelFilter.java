package bz.util.logger.filters;

import lombok.RequiredArgsConstructor;
import bz.util.logger.model.Filter;
import bz.util.logger.model.Record;

@RequiredArgsConstructor
public class LevelFilter implements Filter
{
  public final int lowestLevelToLog;
  public final int highestLevelToLog;

  public LevelFilter(int lowestLevelToLog)
  {
    this(lowestLevelToLog, Integer.MAX_VALUE);
  }

  @Override
  public boolean evaluate(Record record)
  {
    return record.level()>=lowestLevelToLog && record.level()<=highestLevelToLog;
  }

}
