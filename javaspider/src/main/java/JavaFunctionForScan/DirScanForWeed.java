package JavaFunctionForScan;

import JavaFunctionForScan.Utils.CodeUtil;

import javax.imageio.ImageIO;
import javax.rmi.CORBA.Util;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ author ezra
 * @ date 2019/3/13 15:41
 */
public class DirScanForWeed {

	private static ArrayList<Object> scanFiles = new ArrayList<Object>();

	/**
	 * linkedList实现
	 **/
	private static LinkedList<File> queueFiles = new LinkedList<File>();

	public static void orderByDate(String filePath) {
		File file = new File(filePath);
		File[] files = file.listFiles();
		Arrays.sort(files, new Comparator<File>() {
			public int compare(File f1, File f2) {
				long diff = f1.lastModified() - f2.lastModified();
				if (diff > 0)
					return 1;
				else if (diff == 0)
					return 0;
				else
					return -1;// 如果 if 中修改为 返回-1 同时此处修改为返回 1 排序就会是递减
			}

			public boolean equals(Object obj) {
				return true;
			}

		});
		for (int i = 0; i < files.length; i++) {
			System.out.println(files[i].getName());
			System.out.println(new Date(files[i].lastModified()));
		}

	}

	/**
	 * TODO:递归扫描指定文件夹下面的指定文件
	 *
	 * @return ArrayList<Object>
	 * @author 邪恶小先生（LQ）
	 * @time 2017年11月3日
	 */
	public static ArrayList<Object> scanFilesWithRecursion(String folderPath) throws Exception {
		ArrayList<String> dirctorys = new ArrayList<String>();
		File directory = new File(folderPath);
		if (!directory.isDirectory()) {
			throw new Exception('"' + folderPath + '"'
					+ " input path is not a Directory , please input the right path of the Directory. ^_^...^_^");
		}
		if (directory.isDirectory()) {
			File[] filelist = directory.listFiles();
			for (int i = 0; i < filelist.length; i++) {
				/** 如果当前是文件夹，进入递归扫描文件夹 **/
				if (filelist[i].isDirectory()) {
					dirctorys.add(filelist[i].getAbsolutePath());
					/** 递归扫描下面的文件夹 **/
					scanFilesWithRecursion(filelist[i].getAbsolutePath());
				}
				/** 非文件夹 **/
				else {

					File[] filelists = directory.listFiles();
					System.out.println(Arrays.toString(filelists));
					break;
					// scanFiles.add(filelist[i].getAbsolutePath());
					// String[] path = filelist[i].getAbsolutePath().split("\\\\");
					// System.out.println(path.indexOf("\\"));
					// String model = "insert into sense_agro_wiki values(" + "'" + path[2] + "','"
					// + path[3] + "','" +"/static/files/"+path[4] +"')";
					// System.out.println(model);
					// System.out.println(filelist[i].getAbsolutePath());

				}
			}
		}
		return scanFiles;
	}

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


	public ArrayList<Object> scanFilesWithNoRecursion(String folderPath) throws Exception {
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

				for (int j = 0; j < currentFiles.length; j++) {
					if (currentFiles[j].isDirectory()) {

						// 如果仍然是文件夹，将其放入linkedList中
						File[] filelists = currentFiles[j].listFiles();
						// System.out.println(Arrays.toString(filelists));
						Date date = new Date();
						String[] path = filelists[0].getAbsolutePath().split("\\\\");
						String[] typecontent = null;
						String wikiParent = path[2];
						String wikiTitle = path[3];
						String type = "";
						String wikiImage = "";
						String wikiVideo = "";
						StringBuilder wikiContent = null;
						int addTime = (int) ((new Date().getTime() / 1000));
						// 对图片进行处理
						for (int i = 0; i < filelists.length; i++) {
							// System.out.println(path.indexOf("\\"));
							type = filelists[i].toString().substring(filelists[i].toString().lastIndexOf(".") + 1);
							if (type.equals("jpg") || type.equals("png")) {
								// String path1 = "d:/old/1.mp4";
								// File oldName = new File(path1);
								String name = "" + Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(date))
										+ index++;
								String path2 = "d:/script/images20190425_1000/" + name + '.' + type;
								File newName = new File(path2);
								Files.copy(filelists[i].toPath(), newName.toPath());
								// typecontent = filelists[i].getAbsolutePath().split("\\\\");
								wikiImage = wikiImage + "files/wiki_datas/20190425/" + name + '.' + type + ";";
							}

							if (type.equals("mp4")) {
								String name = "" + Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(date))
										+ index++;
								String path2 = "d:/new/" + name + '.' + type;
								File newName = new File(path2);
								Files.copy(filelists[i].toPath(), newName.toPath());
								typecontent = filelists[i].getAbsolutePath().split("\\\\");
								wikiVideo = wikiVideo + "files/wiki_datas/20190425/" + name + '.' + type + ";";
							}

							if (type.equals("txt")) {
								StringBuilder content = new StringBuilder();
								// 对txt文件进行处理。读取txt文件 ansi转utf-8

								String code = CodeUtil.getCode(filelists[i]);
								InputStreamReader read = new InputStreamReader(new FileInputStream(filelists[i]),
										code);
								BufferedReader br = new BufferedReader((read));
								String s = null;
								while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
									// content.append(System.lineSeparator() + s);
									content.append(s);
									// System.out.println(s);
								}
								br.close();
								read.close();
								wikiContent = content;
							}
						}

						model = "insert into sense_agro_wiki (wiki_title,wiki_video,wiki_image,wiki_content,add_time,crop_id,crop_2_id)SELECT'" + wikiTitle + "','"
								+ wikiVideo + "','" + wikiImage + "',\"" + wikiContent + "\"," + addTime + "," + 2 + ",crop_id from sense_agro_crops where crop_name ='" + wikiParent + "';";

						System.out.println(model);
						FileOutputStream fos = null;
						File file = new File("d:/script/weedScan20190425_1000.sql");
						if (!file.exists()) {
							file.createNewFile();
							fos = new FileOutputStream(file);
						}
						fos = new FileOutputStream(file, true);
						OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF8");//指定以UTF-8格式写入文件
						osw.write(model);
						osw.write("\r\n");
						osw.close();
						// if file doesnt exists, then create it

						// System.out.println(Arrays.toString(filelists));
						// queueFiles.add(currentFiles[j]);
					}
				}
			}
		}

		return scanFiles;
	}



	public  ArrayList<Object> updateWikiImageWithNoRecursion(String folderPath) throws Exception {
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

				for (int j = 0; j < currentFiles.length; j++) {
					if (currentFiles[j].isDirectory()) {

						// 如果仍然是文件夹，将其放入linkedList中
						File[] filelists = currentFiles[j].listFiles();
						// System.out.println(Arrays.toString(filelists));
						Date date = new Date();
						String[] path = filelists[0].getAbsolutePath().split("\\\\");
						String[] typecontent = null;
						String wikiParent = path[2];
						String wikiTitle = path[3];
						String type = "";
						String wikiImage = "";
						String wikiVideo = "";
						StringBuilder wikiContent = null;
						int addTime = (int) ((new Date().getTime() / 1000));
						// 对图片进行处理
						for (int i = 0; i < filelists.length; i++) {
							// System.out.println(path.indexOf("\\"));
							type = filelists[i].toString().substring(filelists[i].toString().lastIndexOf(".") + 1);
							if (type.equals("jpg") || type.equals("png")) {
								// String path1 = "d:/old/1.mp4";
								// File oldName = new File(path1);
								String name = "" + Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(date))
										+ index++;
								String path2 = "d:/new/" + name + '.' + type;
								File newName = new File(path2);
								Files.copy(filelists[i].toPath(), newName.toPath());
								// typecontent = filelists[i].getAbsolutePath().split("\\\\");
								wikiImage = wikiImage + "files/wiki_datas/20190329/" + name + '.' + type + ";";
							}
						}
						model = "update sense_agro_wiki set wiki_image ='" +wikiImage+"' where wiki_title = '" 	+ wikiTitle + "';";

						System.out.println(model);
						FileOutputStream fos = null;
						File file = new File("d:/script/test.sql");
						if (!file.exists()) {
							file.createNewFile();
							fos = new FileOutputStream(file);
						}
						fos = new FileOutputStream(file, true);
						OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF8");//指定以UTF-8格式写入文件
						osw.write(model);
						osw.write("\r\n");
						osw.close();
						// if file doesnt exists, then create it

						// System.out.println(Arrays.toString(filelists));
						// queueFiles.add(currentFiles[j]);
					}
				}
			}
		}

		return scanFiles;
	}


	public static void main(String[] args) throws Exception {
		DirScanForWeed scanDir = new DirScanForWeed();
		String path = "d:/杂草汇总";
		scanDir.scanFilesWithNoRecursion(path);
// 		scanDir.orderByDate(path);

//		BufferedReader bre = null;
//		try {
//			String path2 = "D:/杂草百科二期/百合科/麦冬/1.txt";
//			String string = null;
//			bre = new BufferedReader(new FileReader(path2));// 此时获取到的bre就是整个文件的缓存流
//			while ((string = bre.readLine()) != null) // 判断最后一行不存在，为空结束循环
//			{
//				System.out.println(string);// 原样输出读到的内容
//			}
//			bre.close();
//		} catch (Exception e) {
//			System.out.println("sb");
//		}
//		String path1 = "d:/old/1.mp4";

//		String path1 = "d:/" + "old/" + "test.jpg";
//		File oldName = new File(path1);
//		String path2 = "d:/new/2.mp4";
//		File newName = new File(path2);
//		 Files.copy(oldName.toPath(), newName.toPath());
//		 System.out.println(oldName.renameTo(newName));
//		System.out.println(path1.equals(path2));
//		System.out.println(path1+"======"+path2);
//		System.out.println(isImage(oldName));
	}

	public static final boolean isImage(File file) {
		boolean flag = false;
		try {
			BufferedImage bufreader = ImageIO.read(file);
			int width = bufreader.getWidth();
			int height = bufreader.getHeight();
			if (width == 0 || height == 0) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (IOException e) {
			flag = false;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}



	public static String toHex(byte[] byteArray) {
		int i;
		StringBuffer buf = new StringBuffer("");
		int len = byteArray.length;

		for (int offset = 0; offset < len; offset++) {

			i = byteArray[offset];

			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");

			buf.append(Integer.toHexString(i));
		}

		return buf.toString().toUpperCase();
	}

}
