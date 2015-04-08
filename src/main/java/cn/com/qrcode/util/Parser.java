package cn.com.qrcode.util;

/**
 * @author wuxufeng
 * 
 */
public class Parser {
	public static String parseString(Object obj, String def) {
		if (null != obj) {
			try {
				String temp = String.valueOf(obj);
				if (temp.length() > 0) {
					return temp;
				}
			} catch (Exception e) {
			}
		}
		return def;
	}

	public static Integer parseInteger(Object obj, Integer def) {
		if (null != obj) {
			try {
				return Integer.valueOf(obj.toString());
			} catch (Exception e) {
			}
		}
		return def;
	}

}
