package ru.devoir.commons.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import ru.devoir.commons.utils.random.RandomUtil;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class MessageUtil {
    public static String getUTFNumber(int number) {
        switch(number) {
            case 1:
                return "❶";
            case 2:
                return "❷";
            case 3:
                return "❸";
            case 4:
                return "❹";
            case 5:
                return "❺";
            case 6:
                return "❻";
            case 7:
                return "❼";
            case 8:
                return "❽";
            case 9:
                return "❾";
            case 10:
                return "❿";
            default:
                return "0";
        }
    }

    public static String getNumberFormat(int count) {
        return NumberFormat.getNumberInstance(Locale.US).format(count);
    }

    public static String getRandomDieText() {
        String[] texts = new String[]{"§fpress §e[F] §fto pay respect!", "§fтвоя смерть была красивой", "§fI'll be back", "§fну ты и камикадзе :/", "§fне возможно устоять от смерти!", "§fразве это достойная смерть?", "§fуф, а ты опять умер..", "§fну ничего, ты снова в строю!", "§fне огорчайся, такое бывает =)"};
        return texts[(RandomUtil.getRandom()).nextInt(texts.length)];
    }

    public static String getFormatDate() {
        return (new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")).format(new Date());
    }

    public static String getBar(int size, int total, int max, boolean revers) {
        int percent = getProcent(total, size, max, revers);
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i <= size; ++i) {
            String concat = "§8|";
            if (percent > i) {
                concat = "§6|";
            }

            stringBuilder.append(concat);
        }

        return stringBuilder.toString();
    }

    public static String getBar(ChatColor chatColor, String suffix, int size, int total, int max) {
        int procent = getProcent(total, size, max, false);
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i <= size; ++i) {
            String concat = "§8" + suffix;
            if (procent > i) {
                concat = chatColor.toString() + suffix;
            }

            stringBuilder.append(concat);
        }

        return stringBuilder.toString();
    }

    public static int getProcent(int a, int b, int c, boolean revers) {
        int procent = a * b / c;
        if (revers) {
            procent = b - procent;
        }

        return procent;
    }

    public static List<String> getColorList(List<String> lore) {
        List<String> a = new ArrayList<>();

        for (String line : lore) {
            a.add(line.replace('&', '§'));
        }

        return a;
    }

    public static String getTime(int time) {
        return String.format("%02d:%02d", time / 60, time % 60);
    }

    public static String getCorrectWord(int time, String... text) {
        List<String> msg = Arrays.asList(text);
        String single = msg.get(0) + msg.get(1);
        String less_five = msg.get(0) + msg.get(2);
        String others = msg.get(0) + msg.get(3);
        if (time % 100 > 10 && time % 100 < 15) {
            return others;
        } else {
            switch(time % 10) {
                case 1:
                    return single;
                case 2:
                case 3:
                case 4:
                    return less_five;
                default:
                    return others;
            }
        }
    }

    public static String getCompleteTime(int time) {
        long longVal = (new BigDecimal(time)).longValue();
        int hours = (int)longVal / 3600;
        int remainder = (int)longVal - hours * 3600;
        int min = remainder / 60;
        remainder -= min * 60;
        int day = 0;
        if (hours > 24) {
            day = hours / 24;
            hours -= day * 24;
        }

        int years = day / 360;
        day -= 360 * years;
        String y = years + getCorrectWord(years, " ", "год", "года", "лет");
        String d = day + getCorrectWord(day, " д", "ень", "ня", "ней");
        String h = hours + getCorrectWord(hours, " час", "", "а", "ов");
        String m = min + getCorrectWord(min, " минут", "у", "ы", "");
        String s = remainder + getCorrectWord(remainder, " секунд", "у", "ы", "");
        if (years > 5) {
            return "Перманентно";
        } else if (years > 0) {
            return y + " " + d + " " + h + " " + m;
        } else if (day > 0) {
            return d + " " + h + " " + m;
        } else if (hours > 0) {
            return h + " " + m + " " + s;
        } else {
            return min > 0 ? m + " " + s : s;
        }
    }

    public static String replace(String message) {
        return ChatColor.translateAlternateColorCodes('&', hasCenterMessage(message));
    }

    public static String hasCenterMessage(String message) {
        return message.contains("{center}") ? getCenteredMessage(message.replace("{center}", "")) : message;
    }

    public static void sendMessagePrefix(Player p, String message) {
        if (message.contains("{center}")) {
            p.sendMessage(getCenteredMessage(message.replace("{center}", "")));
        } else {
            p.sendMessage(message);
        }

    }

    public static String getCenteredMessage(String message) {
        if (message != null && !message.isEmpty()) {
            message = ChatColor.translateAlternateColorCodes('§', message);
            int messagePxSize = 0;
            boolean previousCode = false;
            boolean isBold = false;
            char[] arrayOfChar;
            int halvedMessageSize = (arrayOfChar = message.toCharArray()).length;

            int toCompensate;
            for(toCompensate = 0; toCompensate < halvedMessageSize; ++toCompensate) {
                char c = arrayOfChar[toCompensate];
                if (c == 167) {
                    previousCode = true;
                } else if (!previousCode) {
                    MessageUtil.DefaultFontInfo dFI = MessageUtil.DefaultFontInfo.getDefaultFontInfo(c);
                    messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                    ++messagePxSize;
                } else {
                    previousCode = false;
                    isBold = c == 'l' || c == 'L';
                }
            }

            halvedMessageSize = messagePxSize / 2;
            toCompensate = 154 - halvedMessageSize;
            int spaceLength = MessageUtil.DefaultFontInfo.SPACE.getLength() + 1;
            int compensated = 0;

            StringBuilder sb;
            for(sb = new StringBuilder(); compensated < toCompensate; compensated += spaceLength) {
                sb.append(" ");
            }

            return sb + message;
        } else {
            return "";
        }
    }

    public enum DefaultFontInfo {
        A('A', 5),
        a('a', 5),
        B('B', 5),
        b('b', 5),
        C('C', 5),
        c('c', 5),
        D('D', 5),
        d('d', 5),
        E('E', 5),
        e('e', 5),
        F('F', 5),
        f('f', 4),
        G('G', 5),
        g('g', 5),
        H('H', 5),
        h('h', 5),
        I('I', 3),
        i('i', 1),
        J('J', 5),
        j('j', 5),
        K('K', 5),
        k('k', 4),
        L('L', 5),
        l('l', 1),
        M('M', 5),
        m('m', 5),
        N('N', 5),
        n('n', 5),
        O('O', 5),
        o('o', 5),
        P('P', 5),
        p('p', 5),
        Q('Q', 5),
        q('q', 5),
        R('R', 5),
        r('r', 5),
        S('S', 5),
        s('s', 5),
        T('T', 5),
        t('t', 4),
        U('U', 5),
        u('u', 5),
        V('V', 5),
        v('v', 5),
        W('W', 5),
        w('w', 5),
        X('X', 5),
        x('x', 5),
        Y('Y', 5),
        y('y', 5),
        Z('Z', 5),
        z('z', 5),
        NUM_1('1', 5),
        NUM_2('2', 5),
        NUM_3('3', 5),
        NUM_4('4', 5),
        NUM_5('5', 5),
        NUM_6('6', 5),
        NUM_7('7', 5),
        NUM_8('8', 5),
        NUM_9('9', 5),
        NUM_0('0', 5),
        EXCLAMATION_POINT('!', 1),
        AT_SYMBOL('@', 6),
        NUM_SIGN('#', 5),
        DOLLAR_SIGN('$', 5),
        PERCENT('%', 5),
        UP_ARROW('^', 5),
        AMPERSAND('§', 5),
        ASTERISK('*', 5),
        LEFT_PARENTHESIS('(', 4),
        RIGHT_PERENTHESIS(')', 4),
        MINUS('-', 5),
        UNDERSCORE('_', 5),
        PLUS_SIGN('+', 5),
        EQUALS_SIGN('=', 5),
        LEFT_CURL_BRACE('{', 4),
        RIGHT_CURL_BRACE('}', 4),
        LEFT_BRACKET('[', 3),
        RIGHT_BRACKET(']', 3),
        COLON(':', 1),
        SEMI_COLON(';', 1),
        DOUBLE_QUOTE('"', 3),
        SINGLE_QUOTE('\'', 1),
        LEFT_ARROW('<', 4),
        RIGHT_ARROW('>', 4),
        QUESTION_MARK('?', 5),
        SLASH('/', 5),
        BACK_SLASH('\\', 5),
        LINE('|', 1),
        TILDE('~', 5),
        TICK('`', 2),
        PERIOD('.', 1),
        COMMA(',', 1),
        SPACE(' ', 3),
        DEFAULT('a', 4);

        private final char character;
        private final int length;

        DefaultFontInfo(char character, int length) {
            this.character = character;
            this.length = length;
        }

        public static MessageUtil.DefaultFontInfo getDefaultFontInfo(char c) {
            MessageUtil.DefaultFontInfo[] arrayOfDefaultFontInfo;
            int i2 = (arrayOfDefaultFontInfo = values()).length;

            for(int i3 = 0; i3 < i2; ++i3) {
                MessageUtil.DefaultFontInfo dFI = arrayOfDefaultFontInfo[i3];
                if (dFI.getCharacter() == c) {
                    return dFI;
                }
            }

            return DEFAULT;
        }

        public char getCharacter() {
            return this.character;
        }

        public int getLength() {
            return this.length;
        }

        public int getBoldLength() {
            return this == SPACE ? this.getLength() : this.length + 1;
        }
    }

    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', "&f" + text);
    }

    public static void sendMessage(Player player, String message) {
        player.sendMessage(colorize(PlaceholderAPI.setPlaceholders(player, message)));
    }

    public static String randomCharacters(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("Invalid length. Length must be at least 1 characters");
        }

        int leftLimit = 97;
        int rightLimit = 122;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

}