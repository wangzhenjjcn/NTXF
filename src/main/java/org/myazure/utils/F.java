package org.myazure.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public final class F {
	public List<File> getFolders(String path) {
		List<File> fileList = new ArrayList<File>();
		File file = new File(path);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length == 0) {
				// System.out.println("文件夹是空的!");
			} else {
				for (File file2 : files) {
					if (file2.isDirectory()) {
						// System.out.println("文件夹:" + file2.getAbsolutePath());
						fileList.add(file2);
						getFolders(file2.getAbsolutePath());
					} else {
						// System.out.println("文件:" + file2.getAbsolutePath());
					}
				}
			}
		} else {
			// System.out.println("文件不存在!");
		}
		return fileList;
	}

	public static List<File> getFileList(String strPath) {
		List<File> filelist = new ArrayList<File>();
		File dir = new File(strPath);
		File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getName();
				if (files[i].isDirectory()) { // 判断是文件还是文件夹
					getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
				} else if (fileName.endsWith("avi")) { // 判断文件名是否以.avi结尾
				// String strFileName = files[i].getAbsolutePath();
					// System.out.println("---" + strFileName);
					filelist.add(files[i]);
				} else {
					continue;
				}
			}
		}
		return filelist;
	}

	public static List<String> getFileListString(String strPath) {
		List<String> filelist = new ArrayList<String>();
		File dir = new File(strPath);
		File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getName();
				if (files[i].isDirectory()) { // 判断是文件还是文件夹
					getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
				} else if (fileName.endsWith("avi")) { // 判断文件名是否以.avi结尾
					String strFileName = files[i].getAbsolutePath();
					// System.out.println("---" + strFileName);
					filelist.add(strFileName);
				} else {
					continue;
				}
			}
		}
		return filelist;
	}

	public static SimpleDateFormat dateTimeFormat() {
		return new SimpleDateFormat("MM-dd HH:mm:ss");
	}

	public static SimpleDateFormat dayFormat() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}

}
