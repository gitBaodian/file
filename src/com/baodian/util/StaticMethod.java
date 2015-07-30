package com.baodian.util;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class StaticMethod {
	public static final String loginError = "{\"status\":1,\"mess\":\"请先登录！\",\"login\":false}";
	public static final String inputError = "{\"status\":1,\"mess\":\"输入有误！\"}";
	public static final String addSucc = "{\"status\":0,\"mess\":\"添加成功！\"}";
	public static final String changeSucc = "{\"status\":0,\"mess\":\"更新成功！\"}";
	public static final String removeSucc = "{\"status\":0,\"mess\":\"删除成功！\"}";
	public static final String authError = "{\"status\":1,\"mess\":\"没有权限！\"}";
	
	/**
	 * 外网代理存在时，也能正确获取ip
	 * @return ip
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		//System.out.println(ip);
		return ip;
	}
	private static Format ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 将时间转换为2013-02-24 22:31:13格式
	 */
	public static String DateToString(Date date) {
		return ft.format(date);
	}
	/**
	 * 将时间转换为2013-02-24 22:31:13格式
	 */
	public static String LongToDate(long l) {
		return ft.format(l);
	}
	/**
	 * json字符串返回
	 */
	public static String jsonMess(int state, String str) {
		return "{\"status\":" + state + ",\"mess\":\"" + str + "\"}";
	}
	/**
	 * 返回字符串长度
	 * @return null:-1
	 */
	public static int StrSize(String str) {
		if(str == null) {
			return -1;
		}
		return str.length();
	}
	/**
	 * 字符串转换成数字
	 * * @return error: -1
	 */
	public static int Str2Int(String i) {
		try {
			return Integer.parseInt(i);
		} catch(Exception e) {
			return -1;
		}
	}
	/**
	 * 是否为ip
	 */
	public static boolean isValidIP(String ipAddress) {
		if(ipAddress == null) {
			return false;
		}
		return Pattern.compile("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}")
				.matcher(ipAddress).matches();
	}
	/**
	 * 将格式化输出文件大小
	 */
	public static String fileSize(long fsize) {
		if(fsize < 1024) {
			return fsize + "B";
		}
		if(fsize >= 1073741824) {
			return fsize/1073741824 + "GB";
		}
		if(fsize >= 1048576) {
			return fsize/1048576 + "MB";
		}
		return fsize/1024 + "KB";
	}
	//是否为ie浏览器
	public static boolean isIE(HttpServletRequest servletRequest) {
		String userAgent = servletRequest.getHeader("User-Agent").toUpperCase();
		if(userAgent.contains("MSIE")) {
			return true;
		}
		//ie 11 => Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko
		if(userAgent.contains("TRIDENT") && userAgent.contains("11.0")) {
			return true;
		}
		return false;
	}
	/**
	 * 类似javascript array的join，将数组转换成字符串，中间用字符隔开
	 * @param array 数组
	 * @param separator 分隔符
	 * @return
	 */
	public static String Set2Str(Set<Integer> array, String separator) {
		if(array.size() == 0) {
			return "";
		}
		StringBuilder str = new StringBuilder();
		for(int i : array) {
			str.append(i + separator);
		}
		return str.substring(0, str.length()-1);
	}
}
