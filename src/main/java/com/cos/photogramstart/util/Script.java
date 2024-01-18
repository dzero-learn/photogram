package com.cos.photogramstart.util;

public class Script {

	public static String back(String msg) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<script>"
				+ "alert('" + msg + "');"
				+ "history.back();"
				+ "</script>");
		return stringBuffer.toString();
	}
}
