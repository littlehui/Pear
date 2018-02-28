package com.pear.commons.tools.data;

import java.util.Random;

/**
 * User: littlehui
 * Date: 14-12-22
 * Time: 下午3:37
 */
public class RandomUtil {
    /**
     * 产生随机字符串
     * */
    private static Random randGen = null;
    private static char[] numbersAndLetters = null;
    private static final Random RANDOM = new Random();
    private static final char[] NUMBERS = "0123456789".toCharArray();
    private static final char[] LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final char[] NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();


    public static final String randomString(int length) {
        if (length < 1) {
            return null;
        }
        if (randGen == null) {
            randGen = new Random();
            numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" +
                    "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
            //numbersAndLetters = ("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
        }
        char [] randBuffer = new char[length];
        for (int i=0; i<randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
            //randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
        }
        return new String(randBuffer);
    }


    /**
     * 产生length长度的字母字符
     *
     * @param length 字符长度
     * @return
     */
    public static String randomLetter(int length) {
        if (length < 1) {
            return null;
        }

        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = LETTERS[RANDOM.nextInt(51)];
        }

        return new String(randBuffer);
    }

    /**
     * 产生length位的随机数字
     *
     * @param length
     * @return
     */
    public static String randomNum(int length) {
        if (length < 1) {
            return null;
        }

        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = NUMBERS[RANDOM.nextInt(10)];
            if (i == 0) {
                if (randBuffer[i] == '0') {
                    i--;
                }
            }
        }

        return new String(randBuffer);
    }

    /**
     * 随即产生min到max之间的随机数,包含max和min
     *
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public static int randomInt(int min, int max) {
        if (min == max) {
            return min;
        }
        return RANDOM.nextInt(max + 1) % (max - min + 1) + min;
    }

}
