package com.pear.commons.tools.math;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class Arith { //默认除法运算精度
    private static final Logger logger = LoggerFactory.getLogger(Arith.class);

    private static final int DEF_DIV_SCALE = 2; //这个类不能实例化

    private Arith() {
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static float add(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.add(b2).floatValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static float sub(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.subtract(b2).floatValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static float mul(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.multiply(b2).floatValue();
    }

    public static float mulWithCut(float v1, float v2) {
       return cut(2, mul(v1, v2));
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static float div(float v1, float v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static float div(float v1, float v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Float.toString(v1));

        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }
    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static float round(float v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Float.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 截断
     * @param cut
     * @param formateValue
     * @return
     */
    public static float cut(int cut, float formateValue) {
        //截断有问题，请不要用
        BigDecimal n = new BigDecimal(formateValue);
        BigDecimal n2 = n.setScale(cut, BigDecimal.ROUND_DOWN);
        return n2.floatValue();
    }

    /**
     * 截断
     * @param cut
     * @param formateValue
     * @return
     */
    public static double cut(int cut, double formateValue) {
        //截断有问题，请不要用
        BigDecimal n = new BigDecimal(formateValue);
        BigDecimal n2 = n.setScale(cut, BigDecimal.ROUND_DOWN);
        return n2.doubleValue();
    }

    /**
     * 截断
     * @param cut
     * @param formatValue
     * @return
     */
    public static double cutDown(int cut, Double formatValue) {
        //截断有问题，请不要用
        BigDecimal n = new BigDecimal(formatValue.toString());
        BigDecimal n2 = n.setScale(cut, BigDecimal.ROUND_DOWN);
        return n2.doubleValue();
    }

    /**
     * 截断
     * @param cut
     * @param formatValue
     * @return
     */
    public static double cutUp(int cut, Double formatValue) {
        BigDecimal n = new BigDecimal(formatValue.toString());
        BigDecimal n2 = n.setScale(cut, BigDecimal.ROUND_CEILING);
        if (logger.isDebugEnabled()) {
            logger.debug("old:" + n + ",new:" + n2);
        }
        return n2.doubleValue();
    }


    /**
     * 判断浮点数相等
     * @param formateValue1
     * @param formateValue2
     * @return
     */
    public static boolean isEqual(float formateValue1, float formateValue2) {
        BigDecimal n1 = new BigDecimal(Float.toString(formateValue1));
        BigDecimal n2 = new BigDecimal(Float.toString(formateValue2));
        return n1.compareTo(n2) == 0 ? true : false;
    }


    /**
     * 判断浮点数相等
     * @param formateValue1
     * @param formateValue2
     * @return
     */
    public static boolean isEqual(double formateValue1, double formateValue2) {
        BigDecimal n1 = new BigDecimal(Double.toString(formateValue1));
        BigDecimal n2 = new BigDecimal(Double.toString(formateValue2));
        return n1.compareTo(n2) == 0 ? true : false;
    }

    /**
     * 判断浮点数 formateValue1 < formateValue2
     * @param formateValue1
     * @param formateValue2
     * @return
     */
    public static boolean isLess(float formateValue1, float formateValue2) {
        BigDecimal n1 = new BigDecimal(formateValue1);
        BigDecimal n2 = new BigDecimal(formateValue2);
        return n1.compareTo(n2) == -1 ? true : false;
    }

    /**
     * 判断浮点数 formateValue1 < formateValue2
     * @param formateValue1
     * @param formateValue2
     * @return
     */
    public static boolean isLess(double formateValue1, double formateValue2) {
        BigDecimal n1 = new BigDecimal(formateValue1);
        BigDecimal n2 = new BigDecimal(formateValue2);
        return n1.compareTo(n2) == -1 ? true : false;
    }

    /**
     * 判断浮点数 formateValue1 > formateValue2
     * @param formateValue1
     * @param formateValue2
     * @return
     */
    public static boolean isGrater(float formateValue1, float formateValue2) {
        BigDecimal n1 = new BigDecimal(formateValue1);
        BigDecimal n2 = new BigDecimal(formateValue2);
        return n1.compareTo(n2) == 1 ? true : false;
    }
    /**
     * 判断浮点数 formateValue1 > formateValue2
     * @param formateValue1
     * @param formateValue2
     * @return
     */
    public static boolean isGrater(double formateValue1, double formateValue2) {
        BigDecimal n1 = new BigDecimal(formateValue1);
        BigDecimal n2 = new BigDecimal(formateValue2);
        return n1.compareTo(n2) == 1 ? true : false;
    }

    public static void main(String[] args) {
        //System.out.println(Arith.mul(0.08571d, 7874521.32d) + "");
        //System.out.println(Arith.mul(0.08571f, 7874521.32f) + "");
        System.out.println(3.1415987557487412f);
    }
}