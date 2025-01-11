package bz.util.logger.timestampproviders;

import java.time.Instant;
import bz.util.logger.model.TimestampProvider;

public class RealtimeTimestampProvider implements TimestampProvider
{

  @Override
  public Instant timestamp()
  {
    return Instant.now();
  }

}
