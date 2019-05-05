package JavaFunctionForScan;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @ author ezra
 * @ date 2019/4/19 15:11
 */
public class DirScanForEnglishWeedForSingleFolder {


	private static ArrayList<Object> scanFiles = new ArrayList<Object>();

	/**
	 * linkedList实现
	 **/
	private static LinkedList<File> queueFiles = new LinkedList<File>();

	public static ArrayList<Object> scanFilesWithNoRecursion(String folderPath) throws Exception {
		File directory = new File(folderPath);
		File[] currentFiles = directory.listFiles();
		String model = null;
		// 如果仍然是文件夹，将其放入linkedList中
		File[] filelists = currentFiles;
		// 对图片进行处理
		for (int i = 0; i < filelists.length; i++) {
			String[] path = filelists[i].getAbsolutePath().split("\\\\");
			int prefix_num=4;
			String wikiTitle=path[4].substring(0,path[4].length() - prefix_num);//得到文件名。去掉了后缀
			String wikiTitleEng = "";
			String type = "";
			StringBuilder wikiContentEng = null;
			// System.out.println(path.indexOf("\\"));
			type = filelists[i].toString().substring(filelists[i].toString().lastIndexOf(".") + 1);
			if (type.equals("txt")) {
				StringBuilder content = new StringBuilder();
				// 对txt文件进行处理。读取txt文件 ansi转utf-8



				InputStreamReader read = new InputStreamReader(new FileInputStream(filelists[i]),
						"utf-8");
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

				String[] contents = wikiContentEng.toString().split("<p>");
				String[] part = contents[1].trim().split("<br>");
				wikiTitleEng = part[1];
				model = " UPDATE sense_agro_wiki SET wiki_content_en = \"" + wikiContentEng + "\",wiki_title_en = \"" + wikiTitleEng + "\"where wiki_title = \"" + wikiTitle + "\";";


				System.out.println(model);
				FileOutputStream fos = null;
				File file = new File("d:/script/updateEnglishWeedWiki20190423.sql");
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
		DirScanForEnglishWeedForSingleFolder dirScanForEnglishWeedForSingleFolder = new DirScanForEnglishWeedForSingleFolder();
		String path = "d:/script/txt/杂草百科";
		dirScanForEnglishWeedForSingleFolder.scanFilesWithNoRecursion(path);
	}


}
