package ru.devoir.commons.utils.temporal;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class SimpleTemporal implements Temporal {
   private long millis;

   private SimpleTemporal(long millis) {
      this.millis = millis;
   }

   public static SimpleTemporal of(long duration, TimeUnit timeUnit) {
      return new SimpleTemporal(timeUnit.toMillis(duration));
   }

   public static SimpleTemporal ofMillis(long millis) {
      return new SimpleTemporal(millis);
   }

   public static SimpleTemporal ofDuration(Duration duration) {
      return new SimpleTemporal(duration.toMillis());
   }

   public static SimpleTemporal ofTemporal(Temporal temporal) {
      return new SimpleTemporal(temporal.get(TimeUnit.MILLISECONDS));
   }

   public static SimpleTemporal zero() {
      return new SimpleTemporal(0L);
   }

   public long get(TimeUnit timeUnit) {
      return timeUnit.convert(this.millis, TimeUnit.MILLISECONDS);
   }

   public Duration toDuration() {
      return Duration.ofMillis(this.millis);
   }

   public boolean isZero() {
      return this.millis == 0L;
   }

   public long getMillis() {
      return this.millis;
   }

   public SimpleTemporal add(Temporal temporal) {
      this.millis += temporal.get(TimeUnit.MILLISECONDS);
      return this;
   }

   public SimpleTemporal multiply(double multiplier) {
      this.millis = (long)((double)this.millis * multiplier);
      return this;
   }

   public boolean biggerThan(Temporal temporal) {
      return this.millis > temporal.get(TimeUnit.MILLISECONDS);
   }

   public boolean smallerThan(Temporal temporal) {
      return this.millis < temporal.get(TimeUnit.MILLISECONDS);
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.millis});
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof SimpleTemporal)) {
         return false;
      } else {
         SimpleTemporal that = (SimpleTemporal)o;
         return this.millis == that.millis;
      }
   }

   public String toString() {
      return "SimpleTemporal{millis=" + this.millis + '}';
   }
}
