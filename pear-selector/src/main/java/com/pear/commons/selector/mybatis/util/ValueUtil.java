package com.pear.commons.selector.mybatis.util;

import org.apache.log4j.Logger;

import java.math.BigDecimal;

/**
 * ��������
 *
 * @author yangz
 * @date   2012-7-28 ����02:42:26
 */
public class ValueUtil {
	private static  final Logger log = Logger.getLogger(ValueUtil.class) ;
	/**
	 * ֵ���� --> String
	 * @param value
	 * @return
	 * @author yangz
	 * @date 2012-7-28 ����03:15:11
	 */
	public static String getString(Object value){
		String result = "";
		if(!ObjectUtil.isEmpty(value)){
			String sValue = value.toString().trim();
			if(value instanceof Number){
				if(value instanceof Double || value instanceof BigDecimal){
					if(!"Infinity".equals(sValue) && !"NaN".equals(sValue)){
						result = StringUtils.toNuSicen(value);
					}else{
						result = "0";
					}
				}else{
					result = sValue;
				}
			}else{
				result = sValue;
			}
		}
		return result.trim();
	}
	/**
	 * ֵ���� --> long
	 * @param value
	 * @return
	 * @author yangz
	 * @date 2012-7-28 ����03:27:21
	 */
	public static long getLong(Object value){
		try {
			return Long.parseLong(getString(value));
		} catch (Exception e) {
			return 0L;
		}
	}
	/**
	 * ֵ���� --> double
	 * @param value
	 * @return
	 * @author yangz
	 * @date 2012-7-28 ����03:29:25
	 */
	public static double getDouble(Object value){
		try {
			return Double.parseDouble(getString(value));
		} catch (Exception e) {
			return 0.0;
		}
	}
	/**
	 * ֵ���� --> int
	 * @param value
	 * @return
	 * @author yangz
	 * @date 2012-7-28 ����03:29:35
	 */
	public static int getInt(Object value){
		try {
			return Integer.parseInt(getString(value));
		} catch (Exception e) {
			return 0;
		}
	}
	/**
	 * ֵ���� --> boolean
	 * @param value
	 * @return
	 * @author yangz
	 * @date 2012-10-12 ����09:00:16
	 */
	public static boolean getBoolean(Object value){
		try {
			String v = getString(value);
			if("1".equals(v)){
				return true;
			}else if("0".equals(v)){
				return false;
			}else if("Y".equals(v)){
				return true;
			}else if("N".equals(v)){
				return false;
			}else{
				return Boolean.parseBoolean(v);
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * �ж�ֵ�Ƿ���0 null ��������
	 * Title: isNullOrZero
	 * Description: 
	 * @param value
	 * @return boolean
	 * @author LittleHui
	 * @date 2014-3-5
	 */
	public static boolean isNullOrZero(Integer value) {
	       try {
	            String v = getString(value);
	            if("0".equals(v)){
	                return true;
	            }else if("null".equals(v)){
	                return true;
	            }else {
	                return false;
	            }
	        } catch (Exception e) {
	            return false;
	        }
	}
}
