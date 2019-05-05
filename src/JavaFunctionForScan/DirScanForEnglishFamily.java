package JavaFunctionForScan;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/**
 * @ author ezra
 * @ date 2019/4/19 15:11
 */
public class DirScanForEnglishFamily {


	private static ArrayList<Object> scanFiles = new ArrayList<Object>();

	/**
	 * linkedList实现
	 **/
	private static LinkedList<File> queueFiles = new LinkedList<File>();

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

			// 如果linkedList非空遍历linkedList
			while (!queueFiles.isEmpty()) {
				// 移出linkedList中的第一个
				File headDirectory = queueFiles.removeFirst();
				File[] currentFiles = headDirectory.listFiles();
				String model = null;
				// 如果仍然是文件夹，将其放入linkedList中
				File[] filelists = currentFiles;
				String[] path = filelists[0].getAbsolutePath().split("\\\\");
				String type;
				int prefix_num=4;
				String wikiTitle = path[2];
				String crop_name_en=path[3].substring(0,path[3].length() - prefix_num);//得到文件名。去掉了后缀
				// 对图片进行处理
				for (int i = 0; i < filelists.length; i++) {
					type = filelists[i].toString().substring(filelists[i].toString().lastIndexOf(".") + 1);
					if (type.equals("txt")) {
						model = "update sense_agro_crops set crop_name_en = " + "'" + crop_name_en +  "' where crop_name = '"+wikiTitle+"';";
					}
				}

				System.out.println(model);
				FileOutputStream fos = null;
				File file = new File("d:/script/insertEnglishCropName0425223.sql");
				if (!file.exists()) {
					file.createNewFile();
					fos = new FileOutputStream(file);
				}
				fos = new FileOutputStream(file, true);
				OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");//指定以UTF-8格式写入文件
				osw.write(model);
				osw.write("\r\n");
				osw.close();
			}
		}

		return scanFiles;

	}


	public static void main(String[] args) throws Exception {
		DirScanForEnglishFamily dirScanForEnglishWeedForSingleFolder = new DirScanForEnglishFamily();
		String path = "d:/英文简易百科 汇总";
		dirScanForEnglishWeedForSingleFolder.scanFilesWithNoRecursion(path);
	}
}
