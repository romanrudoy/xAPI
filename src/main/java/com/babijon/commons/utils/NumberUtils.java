/* Decompiler 13ms, total 210ms, lines 82 */
package com.babijon.commons.utils;

import lombok.experimental.UtilityClass;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;
import java.util.OptionalInt;

@UtilityClass
public final class NumberUtils {
   private final DecimalFormat[] DECIMAL_FORMATS = new DecimalFormat[10];
   private final DecimalFormat currencyFormat;

   private DecimalFormat createDecimalFormat(int digitsAfterDecimal) {
      char[] chars = new char[digitsAfterDecimal];
      Arrays.fill(chars, '#');
      DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
      otherSymbols.setDecimalSeparator('.');
      return new DecimalFormat("#." + new String(chars), otherSymbols);
   }

   public String formatDouble(double d, int digitsAfterDecimal) {
      return format(d, DECIMAL_FORMATS[digitsAfterDecimal - 1]);
   }

   public String formatFloat(float f, int digitsAfterDecimal) {
      return format((double)f, DECIMAL_FORMATS[digitsAfterDecimal - 1]);
   }

   public String formatCurrency(double currency) {
      return format(currency, currencyFormat);
   }

   private String format(double d, DecimalFormat decimalFormat) {
      int i = (int)d;
      return (double)i == d ? String.valueOf(i) : decimalFormat.format(d);
   }

   public double pythagoras(double... values) {
      return Math.sqrt(sumOfSquares(values));
   }

   public double pythagoras(double a, double b) {
      return Math.sqrt(sumOfSquares(a, b));
   }

   public double sumOfSquares(double a, double b) {
      return square(a) + square(b);
   }

   public double sumOfSquares(double... values) {
      return Arrays.stream(values).map(NumberUtils::square).sum();
   }

   public double square(double num) {
      return num * num;
   }

   public double getSign(double num) {
      return num < 0.0D ? -1.0D : (num == 0.0D ? 0.0D : 1.0D);
   }

   public OptionalInt parseInt(String s) {
      try {
         return OptionalInt.of(Integer.parseInt(s));
      } catch (NumberFormatException var2) {
         return OptionalInt.empty();
      }
   }

   static {
      currencyFormat = new DecimalFormat("#0.00", DecimalFormatSymbols.getInstance(Locale.US));

      for(int i = 0; i < DECIMAL_FORMATS.length; ++i) {
         DECIMAL_FORMATS[i] = createDecimalFormat(i + 1);
      }

   }
}
