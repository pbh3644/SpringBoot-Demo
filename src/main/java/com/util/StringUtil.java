package com.util;

/**
 * @author: pangbohuan
 * @className: StringUtil
 * @Date：2018-11-09 14:18
 * @description: (字符串工具类)
 */
public final class StringUtil {
    private static final char SEPARATOR = '_';


    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = NumberUtil.ZERO; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - NumberUtil.ONE)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + NumberUtil.ONE));
            }

            if (i > NumberUtil.ZERO && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }
}
