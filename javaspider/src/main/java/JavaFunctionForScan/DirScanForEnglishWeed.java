package JavaFunctionForScan;

import JavaFunctionForScan.Utils.CodeUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @ author ezra
 * @ date 2019/4/19 15:11
 */
public class DirScanForEnglishWeed {


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

				// 对图片进行处理
				for (int j = 0; j < currentFiles.length; j++) {
					if (currentFiles[j].isDirectory()) {
						// 如果仍然是文件夹，将其放入linkedList中
						File[] filelists = currentFiles[j].listFiles();
						// System.out.println(Arrays.toString(filelists));

						String[] path = filelists[0].getAbsolutePath().split("\\\\");

						String wikiTitle = path[3];
						String type = "";
						String wikiTitleEng = "";
						StringBuilder wikiContentEng = null;
						// 对图片进行处理
						for (int i = 0; i < filelists.length; i++) {
							// System.out.println(path.indexOf("\\"));
							type = filelists[i].toString().substring(filelists[i].toString().lastIndexOf(".") + 1);

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
								wikiContentEng = content;
							}
						}
						String[] contents = wikiContentEng.toString().split("<p>");
						String[] part = contents[1].trim().split("<br>");
						wikiTitleEng = part[1];
						model = " UPDATE sense_agro_wiki SET wiki_content_en = \"" + wikiContentEng + "\",wiki_title_en = \"" + wikiTitleEng + "\"where wiki_title = \"" + wikiTitle + "\";";


						System.out.println(model);
						FileOutputStream fos = null;
						File file = new File("d:/script/updateEnglishWeedWiki20190426Error.sql");
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
		DirScanForEnglishWeed dirScanForEnglishWeed = new DirScanForEnglishWeed();
		String path = "D:/errorscan";
		dirScanForEnglishWeed.scanFilesWithNoRecursion(path);

	}

}
