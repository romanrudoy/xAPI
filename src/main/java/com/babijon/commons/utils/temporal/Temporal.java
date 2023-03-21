package com.babijon.commons.utils.temporal;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public interface Temporal {
   long get(TimeUnit var1);

   Duration toDuration();

   boolean isZero();

   default String format() {
      return TemporalUtils.formatTemporal(this);
   }
}
