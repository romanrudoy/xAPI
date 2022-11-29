package ru.devoir.commons.utils.temporal;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public final class TemporalUtils {
   private static final Map<String, ChronoUnit> SYMBOL_UNIT_MAP = new HashMap();
   private static final Map<ChronoUnit, String[]> DECLINED_WORDS_MAP = new EnumMap(ChronoUnit.class);

   private TemporalUtils() {
   }

   private static void putDeclinedWord(ChronoUnit chronoUnit, String... words) {
      DECLINED_WORDS_MAP.put(chronoUnit, words);
   }

   public static Temporal parseTemporal(String time) {
      Duration duration = Duration.ZERO;
      String[] var2 = time.toLowerCase().split(" ");

      for (String s : var2) {
         for (String t : SYMBOL_UNIT_MAP.keySet()) {
            if (s.endsWith(t)) {
               long multiplicand;
               try {
                  multiplicand = Long.parseLong(s.substring(0, s.length() - 1));
               } catch (NumberFormatException var11) {
                  return SimpleTemporal.ofMillis(0L);
               }

               duration = duration.plus(SYMBOL_UNIT_MAP.get(t).getDuration().multipliedBy(multiplicand));
               break;
            }
         }
      }

      return SimpleTemporal.ofDuration(duration);
   }

   public static String formatTemporal(Temporal temporal) {
      StringJoiner joiner = new StringJoiner(" ");
      Duration duration = temporal.toDuration();
      long d = duration.toDays();
      if (d > 0L) {
         joiner.add(Long.toString(d)).add(((String[])DECLINED_WORDS_MAP.get(ChronoUnit.DAYS))[getDeclensionGroup(d)]);
         duration = duration.minusDays(d);
      }

      long h = duration.toHours();
      if (h > 0L) {
         joiner.add(Long.toString(h)).add(((String[])DECLINED_WORDS_MAP.get(ChronoUnit.HOURS))[getDeclensionGroup(h)]);
         duration = duration.minusHours(h);
      }

      long m = duration.toMinutes();
      if (m > 0L) {
         joiner.add(Long.toString(m)).add(((String[])DECLINED_WORDS_MAP.get(ChronoUnit.MINUTES))[getDeclensionGroup(m)]);
         duration = duration.minusMinutes(m);
      }

      long s = duration.getSeconds();
      if (s > 0L) {
         joiner.add(Long.toString(s)).add(((String[])DECLINED_WORDS_MAP.get(ChronoUnit.SECONDS))[getDeclensionGroup(s)]);
      }

      return joiner.toString();
   }

   public static String formatTime(long millis) {
      StringJoiner joiner = new StringJoiner(" ");
      Duration duration = Duration.ofMillis(millis);
      long d = duration.toDays();
      if (d > 0L) {
         joiner.add(Long.toString(d)).add(((String[])DECLINED_WORDS_MAP.get(ChronoUnit.DAYS))[getDeclensionGroup(d)]);
         duration = duration.minusDays(d);
      }

      long h = duration.toHours();
      if (h > 0L) {
         joiner.add(Long.toString(h)).add(((String[])DECLINED_WORDS_MAP.get(ChronoUnit.HOURS))[getDeclensionGroup(h)]);
         duration = duration.minusHours(h);
      }

      long m = duration.toMinutes();
      if (m > 0L) {
         joiner.add(Long.toString(m)).add(((String[])DECLINED_WORDS_MAP.get(ChronoUnit.MINUTES))[getDeclensionGroup(m)]);
         duration = duration.minusMinutes(m);
      }

      long s = duration.getSeconds();
      if (s > 0L) {
         joiner.add(Long.toString(s)).add(((String[])DECLINED_WORDS_MAP.get(ChronoUnit.SECONDS))[getDeclensionGroup(s)]);
      }

      return joiner.toString();
   }

   private static int getDeclensionGroup(long n) {
      long k = n % 10L;
      return k != 0L && k < 5L && (n < 11L || n > 20L) ? (k == 1L ? 0 : 2) : 1;
   }

   static {
      SYMBOL_UNIT_MAP.put("d", ChronoUnit.DAYS);
      SYMBOL_UNIT_MAP.put("h", ChronoUnit.HOURS);
      SYMBOL_UNIT_MAP.put("m", ChronoUnit.MINUTES);
      SYMBOL_UNIT_MAP.put("s", ChronoUnit.SECONDS);
      putDeclinedWord(ChronoUnit.DAYS, "день", "дней", "дня");
      putDeclinedWord(ChronoUnit.HOURS, "час", "часов", "часа");
      putDeclinedWord(ChronoUnit.MINUTES, "минута", "минут", "минуты");
      putDeclinedWord(ChronoUnit.SECONDS, "секунда", "секунд", "секунды");
   }
}
