package com.pear.commons.tools.data;

import org.apache.log4j.Logger;

import java.security.MessageDigest;

public class MD5Util {
	
	private static Logger logger = Logger.getLogger(MD5Util.class);
	private static final char[] HEX_CHARS = {
		'0', '1', '2', '3', 
		'4', '5', '6', '7',
		'8', '9', 'a', 'b',
		'c', 'd', 'e', 'f'
	};
    public static String md5(String message) {
    	if (message == null) message = "";
    	try {
    		MessageDigest md = MessageDigest.getInstance("MD5");
    		md.update(message.getBytes("UTF8"));
    		byte[] bytes = md.digest();
    		int len = bytes.length;
    		
    		char chars[] = new char[len * 2];
    		for (int i=0,k=0; i<len; i++) {
    			byte b = bytes[i];
    			chars[k++] = HEX_CHARS[(b >>> 4) & 0x0f];
    			chars[k++] = HEX_CHARS[b & 0x0f];
    		}
    		
    		return new String(chars);
    	}
    	catch (Exception ex) {
    		logger.error("md5 error", ex);
    		return "";
    	}
    }

	public static void main(String[] args) {
		String md5Code = MD5Util.md5("113671005");
		System.out.println(md5Code);
		System.out.println(md5Code.substring(0,2));
		System.out.println(md5Code.substring(2,4));
		System.out.println("https://s.i.17173cdn.com/avatar/YWxqaGBf/"
				+md5Code.substring(0,2)+"/"
				+md5Code.substring(2,4)+"/"
				+md5Code.substring(4,6)+"/"
				+113671005+"/normal.jpg");
	}
}
