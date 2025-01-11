package bz.util.logger.model;

import java.time.Instant;

public interface TimestampProvider
{
  Instant timestamp();
}
