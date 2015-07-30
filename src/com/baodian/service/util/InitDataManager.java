package com.baodian.service.util;

import java.util.HashSet;
import java.util.Set;

public interface InitDataManager {
	/**
	 * 所有允许上传的扩展名
	 */
	public Set<String> extSet = new HashSet<String>();
	public Set<String> imageSet = new HashSet<String>();
	/**
	 * 0-image 1-flash 2-media 3-file 4-uploadDir
	 * 5-dirSize 6-fileSize
	 */
	public static String[] upload = new String[7];
	/**
	 * 0-dirSize 1-fileSize
	 */
	public static long[] longPms = new long[2];
	//四班三倒
	//上班顺序
	public static String[] dutyName = new String[4];
	//白班顺序
	public static int[] dutyOrder = new int[4];
	
	/**
	 * 重新读取数据
	 */
	public void reload();
	/**
	 * 输出数据
	 */
	public void output();
}
