package com.lkl.java8;

import java.io.File;
import java.io.FileFilter;
import java.util.Comparator;
import java.util.List;

import scala.actors.threadpool.Arrays;

public class LambdaT {

	public static void main(String[] args) {
		File dir = new File("D:\\");
		FileFilter directoryFilter = (File f) -> f.isDirectory();
		File[] dirs = dir.listFiles(directoryFilter);
		
		List<File> dlist = Arrays.asList(dirs);
		
		dlist.forEach((d)->System.out.println(d+";"));
		
		new Thread(()->System.out.println("1111")).start();
		
		Runnable runnable =  ()->System.out.println("1111");
		runnable.run();
		
		String[] players = {"Rafael Nadal", "Novak Djokovic",   
			    "Stanislas Wawrinka", "David Ferrer",  
			    "Roger Federer", "Andy Murray",  
			    "Tomas Berdych", "Juan Martin Del Potro",  
		 	    "Richard Gasquet", "John Isner"};  
		
		Comparator<String> comparable = (String s1,String s2) -> s1.compareTo(s2);
		Arrays.sort(players,comparable);  
		
		List<String> dlistd = Arrays.asList(players);
		dlistd.forEach(d->System.out.println(d));
		
		
		// 在 Java 8 中使用双冒号操作符(double colon operator)  
		dlistd.forEach(System.out::println);  
	}

}
