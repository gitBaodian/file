package com.baodian.service.util.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.baodian.service.util.InitDataManager;

@Component("initData")
public class InitDataManagerImpl implements InitDataManager {

	@PostConstruct
	public void init() {
		InputStream in = this.getClass().getResourceAsStream("/config.properties");
		Properties properties = new Properties();
		try {
			properties.load(in);
			upload[0] = properties.getProperty("image");
			upload[1] = properties.getProperty("flash");
			upload[2] = properties.getProperty("media");
			upload[3] = properties.getProperty("file");
			for(String s : upload[0].split(",")) {
				extSet.add(s);
				imageSet.add(s);
			}
			for(String s : upload[1].split(",")) {
				extSet.add(s);
			}
			for(String s : upload[2].split(",")) {
				extSet.add(s);
			}
			for(String s : upload[3].split(",")) {
				extSet.add(s);
			}
			upload[4] = properties.getProperty("uploadDir");
			longPms[0] = Long.parseLong(properties.getProperty("dirSize"));
			longPms[1] = Long.parseLong(properties.getProperty("fileSize"));
			upload[5] = fileSize(longPms[0]);
			upload[6] = fileSize(longPms[1]);
			//四班三倒
			String[] str = properties.getProperty("dutyName").split(",");
			for(int i=0; i<str.length; i++) {
				dutyName[i] = str[i];
			}
			str = properties.getProperty("dutyOrder").split(",");
			for(int i=0; i<str.length; i++) {
				dutyOrder[i] = Integer.parseInt(str[i]);
			}
			System.out.println("***初始化InitData(配置数据)成功***");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 将格式化输出文件大小
	 */
	private String fileSize(long fsize) {
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
//r
	public void reload() {
		extSet.clear();
		imageSet.clear();
		this.init();
	}
//o
	public void output() {
		System.out.println("---扩展名0-image 1-flash 2-media 3-file 4-uploadDir");
		for(String s: upload) {
			System.out.println(s);
		}
		printSet("---图片扩展imageSet", imageSet);
		printSet("---全部扩展extSet", extSet);
		printString("---值班名称dutyName", dutyName);
		printInt("---值班顺序dutyOrder", dutyOrder);
	}
	private void printSet(String name, Set<String> o) {
		System.out.print(name + "(" + o.size() + ") => ");
		for(String s : o) {
			System.out.print(s + " ");
		}
		System.out.println();
	}
	private void printString(String name, String[] o) {
		System.out.print(name + "(" + o.length + ") => ");
		for(String s : o) {
			System.out.print(s + " ");
		}
		System.out.println();
	}
	private void printInt(String name, int[] o) {
		System.out.print(name + "(" + o.length + ") => ");
		for(int s : o) {
			System.out.print(s + " ");
		}
		System.out.println();
	}
}
