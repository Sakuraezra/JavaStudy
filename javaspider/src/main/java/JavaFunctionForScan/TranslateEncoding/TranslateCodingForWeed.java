package JavaFunctionForScan.TranslateEncoding;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/**
 * @ author ezra
 * @ date 2019/4/19 16:58
 */
public class TranslateCodingForWeed {


	private static ArrayList<Object> scanFiles = new ArrayList<Object>();

	/**
	 * linkedList实现
	 **/
	private static LinkedList<File> queueFiles = new LinkedList<File>();


	/**
	 *
	 * TODO:非递归方式扫描指定文件夹下面的所有文件
	 *
	 * @return ArrayList<Object>
	 * @param folderPath 需要进行文件扫描的文件夹路径
	 * @author 邪恶小先生（LQ）
	 * @time 2017年11月3日
	 */
	/**
	 * TODO:非递归方式扫描指定文件夹下面的所有文件并写入sql脚本
	 *
	 * @param folderPath 需要进行文件扫描的文件夹路径
	 * @return ArrayList<Object>
	 * @author ezra
	 * @time 2019年1月18日
	 */

	public static ArrayList<Object> scanFilesWithNoRecursion(String folderPath) throws Exception {
		File directory = new File(folderPath);
		int index = 0;
		if (!directory.isDirectory()) {
			throw new Exception('"' + folderPath + '"'
					+ " input path is not a Directory , please input the right path of the Directory. ^_^...^_^"
					+ "It's impossible");
		} else {
			// 首先将第一层目录扫描一遍
			File[] files = directory.listFiles();
			// 遍历扫出的文件数组，如果是文件夹，将其放入到linkedList中稍后处理
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					// System.out.println(files[i]);
					queueFiles.add(files[i]);
				} else {
					// 暂时将文件名放入scanFiles中
					scanFiles.add(files[i].getAbsolutePath());
				}
			}

			while (!queueFiles.isEmpty()) {
				// 移出linkedList中的第一个
				File headDirectory = queueFiles.removeFirst();
				File[] currentFiles = headDirectory.listFiles();

				for (int j = 0; j < currentFiles.length; j++) {
					if (currentFiles[j].isDirectory()) {
						// 如果仍然是文件夹，将其放入linkedList中
						File[] filelists = currentFiles[j].listFiles();
						// System.out.println(Arrays.toString(filelists));
						Date date = new Date();
						String[] path = filelists[0].getAbsolutePath().split("\\\\");
						String filename = path[3];
						String type = "";
						// 对图片进行处理
						for (int i = 0; i < filelists.length; i++) {
							// System.out.println(path.indexOf("\\"));
							type = filelists[i].toString().substring(filelists[i].toString().lastIndexOf(".") + 1);
							if (type.equals("txt")) {
								StringBuilder content = new StringBuilder();
								// 对txt文件进行处理。读取txt文件 ans
								// i转utf-8
								InputStreamReader read = new InputStreamReader(new FileInputStream(filelists[i]),
										"GBK");
								File file = new File("d:/script/txt/杂草百科test/" + filename + ".txt");
								OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8"); //存为UTF-8
								int len = read.read();
								while (-1 != len) {
									osw.write(len);
									len = read.read();
								}
								//刷新缓冲区的数据，强制写入目标文件
								osw.flush();
								osw.close();
								read.close();
							}
						}
					}
				}
			}
		}
		return scanFiles;
	}

	public static void main(String[] args) throws Exception {
		TranslateCodingForWeed translateCoding = new TranslateCodingForWeed();
		String path = "d:/第一批92种完整杂草英文百科--有水印--部署上线";
		translateCoding.scanFilesWithNoRecursion(path);


	}


}
