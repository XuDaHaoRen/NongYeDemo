package com.xbl.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;

import android.content.Context;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

/**
 * clearCache()
 * 清理缓存文件  1.GC清理 2.清理内部Cache文件和SD卡的Cache文件
 * deleteDir(File dir)
 * 通过递归的方法判断文件是文件还是文件夹，如果是文件夹就继续递归，如果是文件则删除
 * 最后再将空的文件夹删除
 * getAllCacheFileSize()
 * 得到内部存储和外部存储的所有缓存文件的大小
 * bytesToString(long size)
 * 文件大小的转换
 * getDirSize(File dir)
 * 判断文件/文件夹的大小  如果是文件夹就不断地递归每次递归都将里面的文件大小相加
 * createFile(File dir,String name,String content,int foreach)
 * 创建垃圾文件 路径(在内部和外部文件的Cache目录下) 名称，当前环境，添加的垃圾
 *
 */

public class FileUtils {

	private Context context;

	private FileUtils() {

	}

	private static FileUtils dao = new FileUtils();

	public static FileUtils with(Context contet) {
		dao.context = contet;
		return dao;
	}

	//清空缓存文件
	public boolean clearCache(){
		//清空缓存，通过GC清除内存
		System.gc();

		//清空内部存储的缓存文件
		deleteDir(context.getCacheDir());

		//外部存储的缓存文件
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			deleteDir(context.getExternalCacheDir());
		}

		Toast.makeText(context, "清理完毕！", Toast.LENGTH_SHORT).show();
		//设置显示
	//	tv.setText("OK");
		return true;
	}

	//删除文件夹
	private void deleteDir(File dir){
		//需要先删除文件，没有文件的文件夹可以删除
		File[] childs = dir.listFiles();
		int len = childs.length;
		for (int i = 0; i < len; i++) {
			File f = childs[i];
			if (f.isFile()) {
				f.delete(); //删除文件
			}else{
				deleteDir(f);
			}
		}

		dir.delete(); //删除文件夹
	}

	//获取垃圾文件的大小
	public String getAllCacheFileSize(){

		long size = 0;
		//内部存储的缓存文件大小
		size += getDirSize(context.getCacheDir());

		//外部存储的缓存文件大小
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			size += getDirSize(context.getExternalCacheDir());
		}

		//转化成KB,MB,GB
		return bytesToString(size);
	}

	//大小的转化
	private String bytesToString(long size){

		//0.01KB不需要清理
		double kb = 1.0*size/1024;
		if (kb < 0.01) {
			return "0K";
		}

		double mb = kb/1024;
		if (mb < 1) {
			//kb做单位
			BigDecimal kbmal = new BigDecimal(kb);
			return kbmal.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()+"KB";
		}

		double gb = mb/1024;
		if (gb < 1) {
			//mb做单位
			BigDecimal mbmal = new BigDecimal(mb);
			return mbmal.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()+"MB";
		}

		//gb作为单位
		BigDecimal gbmal = new BigDecimal(gb);
		return gbmal.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()+"GB";
	}

	// 垃圾文件
	public void gcFiles() {
		//内部存储垃圾文件
		File innerDir = context.getCacheDir();
		createFile(innerDir, "aa.txt", "ajajdjdjj", 1000);
		createFile(innerDir, "bb.txt", "ajajdjdjj", 1000);
		createFile(innerDir, "cc.txt", "ajajdjdjj", 1000);
		createFile(new File(innerDir,"abc"), "aa.txt", "adminadmiamdin", 1000);
		createFile(new File(innerDir,"abc"), "bb.txt", "adminadmiamdin", 1000);
		createFile(new File(innerDir,"abc"), "cc.txt", "adminadmiamdin", 1000);
		createFile(new File(innerDir,"aa"+File.separator+"cc"), "temp1.txt", "hello hello", 1000);
		createFile(new File(innerDir,"aa"+File.separator+"cc"), "temp2.txt", "hello hello", 1000);

		//外部存储的垃圾文件
		File outterDir = context.getExternalCacheDir();
		createFile(outterDir, "login1.txt", "amadajdjadjad", 1000);
		createFile(outterDir, "login2.txt", "amadajdjadjad", 1000);
		createFile(outterDir, "login3.txt", "amadajdjadjad", 1000);
		createFile(new File(outterDir,"user"), "user1.txt", "andnadnan", 1000);
		createFile(new File(outterDir,"user"), "user2.txt", "andnadnan", 1000);
		createFile(new File(outterDir,"user"), "user3.txt", "andnadnan", 1000);
	}

	//创建文件
	private void createFile(File dir,String name,String content,int foreach){

		//判断文件是否存在，不存在就级联创建
		if (!dir.exists()) {
			dir.mkdirs();
		}

		//打开文件的输入流
		try {
			FileOutputStream fos = new FileOutputStream(dir+File.separator+name);
			for (int i = 0; i < foreach; i++) {
				//保存数据
				fos.write(content.getBytes());
			}
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取文件夹的大小
	private long getDirSize(File dir) {
		long size = 0;
		// 文件夹，遍历所有的文件
		File[] childs = dir.listFiles();
		int len = childs.length;
		for (int i = 0; i < len; i++) {
			File f = childs[i];
			if (f.isFile()) {
				size += f.length();
			} else {
				// 文件夹
				size += getDirSize(f);
			}
		}

		return size;
	}
}
