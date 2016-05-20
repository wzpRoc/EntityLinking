package org.ailab.tem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * author: OneChen 
 * time  : 2013-11-21
 * type  : code_counting
 */

public class CodeCountCal {
	
	private long codeLine;
	private long whiteLine;
	private long commentLine;
	
	public CodeCountCal() {
		codeLine = 0;
		whiteLine = 0;
		commentLine = 0;
	}
	
	public void printResults(String fileName) {
		System.out.println(fileName+":");
		System.out.println("comments:"+commentLine);
		System.out.println("blacks:"+whiteLine);
		System.out.println("codes:"+codeLine);
		System.out.println();
	}
	
	public void cleanCount() {
		codeLine = 0;
		whiteLine = 0;
		commentLine = 0;
	}
	
	
	public void countCode(File f) {
		BufferedReader  br=null;
        boolean bln=false;
        try {
        	br=new BufferedReader(new FileReader(f));
            String  line="";
            try {
                while((line = br.readLine()) != null) {
                    line=line.trim();
                    if(line.matches("^[\\n]*$")){
                        whiteLine+=1;
                    }else if(line.startsWith("/*")&&!line.equals("*/")){
                        commentLine+=1;
                        bln=true;
                    }else if (bln==true){
                        commentLine+=1;
                        if(line.endsWith("*/")){
                            bln=false;
                        }
                    }else if(line.startsWith("/*")&&line.endsWith("*/")){
                        commentLine+=1;
                    }else if(line.startsWith("//")){
                        commentLine+=1;
                    }else {
                    //	System.out.println(line);
                        codeLine+=1;
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }catch(FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(br!=null){
                try {
                    br.close();
                    br=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
            }
        }
	}
	
	public static void main(String[] args) {
		long totalCodes = 0;
		long totalBlanks = 0;
		long totalComments = 0;
		CodeCountCal codeCountCal = new CodeCountCal();
		String path1 = "D:\\projects\\svn\\MusicPlayer\\MusicSite\\src\\org\\ailab\\tem";
		File structFile = new File(path1);
		File[] structFiles = structFile.listFiles();
		for(int i=0; i<structFiles.length; i++){
			if(structFiles[i].getName().matches(".*\\.java$")) {
				codeCountCal.countCode(structFiles[i]);
			}
			codeCountCal.printResults(structFiles[i].getName());
			totalCodes += codeCountCal.codeLine;
			totalBlanks +=codeCountCal.whiteLine;
			totalComments += codeCountCal.commentLine;
			codeCountCal.cleanCount();
			
		}
		/*
		String path = "D:\\Projects\\WIMFra\\WIMFraProject\\src\\org\\ailab\\teamworkPlanner\\wim";
		File dirFile = new File(path);
		File[] dirFiles =dirFile.listFiles();		
		for(int i=0; i<dirFiles.length; i++){
			File javaFile = new File(path+"\\"+dirFiles[i].getName());
			File[] files = javaFile.listFiles();
			for (File eachfile:files){
				if(eachfile.getName().matches(".*\\.java$")) {
					codeCountCal.countCode(eachfile);
				}
			}
			codeCountCal.printResults(dirFiles[i].getName());
			totalCodes += codeCountCal.codeLine;
			totalBlanks +=codeCountCal.whiteLine;
			totalComments += codeCountCal.commentLine;
			codeCountCal.cleanCount();
		}
		*/
		System.out.println("totalCodes:"+totalCodes);
		System.out.println("totalBlanks:"+totalBlanks);
		System.out.println("totalComments:"+totalComments);
	}
	

}
