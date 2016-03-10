package com.lkl.java8;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import scala.actors.threadpool.Arrays;

public class LambdaT {

	public static void main(String[] args) {
		File dir = new File("D:\\");
		FileFilter directoryFilter = (File f) -> f.isDirectory();
		File[] dirs = dir.listFiles(directoryFilter);
		
		List<File> dlist = Arrays.asList(dirs);
		
		dlist.forEach((d)->System.out.println(d+";"));
	}

}
