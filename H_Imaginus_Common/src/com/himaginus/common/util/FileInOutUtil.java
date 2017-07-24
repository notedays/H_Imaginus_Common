package com.himaginus.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class FileInOutUtil {
	
	public String folderSrc = "C:/H_Imaginus/";
	
	public FileInOutUtil() {}
	public FileInOutUtil(String folderSrc) {
		setFolderSrc(folderSrc);
	}
	
	public String getFolderSrc() {
		return folderSrc;
	}
	public void setFolderSrc(String folderSrc) {
		if(folderSrc.charAt(folderSrc.length()-1) != '/'){
			folderSrc = folderSrc.concat("/");
		}
		this.folderSrc = folderSrc;
	}
	
	/**
	 * # FOLDER_SRC 에 저장된 폴더 안에 파라미터로 받은 이름의 파일 및 폴더를 자동 생성해주는 메소드.
	 * @param fileName : 만들고자 하는 파일이름
	 * @param autoMkdir : 폴더를 자동으로 생성 해줄지 말지 결정하는 플래그
	 * @return
	 */
	private File makeFile(String fileName, boolean autoMkdir){
		if( autoMkdir ) folderChecker();
		return new File(folderSrc+fileName);
	}
	
	/**
	 * # 폴더 존재 여부 확인 후 없다면 폴더 생성!
	 */
	private void folderChecker(){
		File folder = new File(folderSrc);
		if(!folder.exists()){
			folder.mkdirs();
			System.out.println("Folder Make Process : ["+folderSrc+"] make..!!" );
		}
	}
	
	// # 바이트배열 출력하기
	@SuppressWarnings("unused")
	private void printBytes(byte[] bytes){
		System.out.println(" =================== 바이트 출력 Start ======================");
		int limit = 20;
		for (int i = 0; i < bytes.length; i++) {
			System.out.print(bytes[i]);
			if(i%limit != limit-1) System.out.print(" ,");
			else System.out.println();
		}
		System.out.println("\n =================== 바이트 출력 End ======================");
	}
	
	/**
	 * # 리턴 타입(byte[], Bean Class)에 따라 해당 파일을 읽은 후  
	 * @param fileName
	 * @param obj
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	private <T> T readFile(String fileName, T returnType) {
		File file = makeFile(fileName, false);
		try{
			if(returnType instanceof byte[]){
				try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
					byte[] bytes = new byte[in.available()];
					in.read(bytes);
					return (T) bytes;
				}
			}else if(returnType instanceof Serializable){
				try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
					return (T) in.readObject();
				}
			}
			System.out.println("## 파일 읽기 : "+file.getAbsolutePath());
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * # 데이터 타입에 따라 byte[] 또는 Bean Class의 파일 생성 메소드
	 * @param fileName : 파일명
	 * @param data : 파일로 쓸 데이터 (byte[], Bean Class)
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean writeFile(String fileName, Object data){
		File file = makeFile(fileName, true);
		try{	
			// # 객체 자체를 파일 입출력 시..
			if(data instanceof byte[]){
				try(BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))){
					out.write((byte[])data);
					out.flush();
				}
			}else if(data instanceof Serializable){
				try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file)) ){
					out.writeObject(data);
					out.flush();
				}
			}
			System.out.println("## 파일 쓰기 : "+file.getAbsolutePath());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) {
		new FileInOutUtil("c:/H_Imaginus");
	}
}
