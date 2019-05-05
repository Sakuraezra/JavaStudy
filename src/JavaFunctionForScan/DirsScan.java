package JavaFunctionForScan;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @ author ezra
 * @ date 2019/4/23 10:43
 */
public class DirsScan {

/**
 *
 */

	/**
	 * @author ezra
	 */

	public static void main(String[] args) {
		String path = "d:/英文简易百科 汇总";
		try {
			DirsScan.dirsScan(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void dirsScan(String filePath) throws IOException {
		File directory = new File(filePath);
		File[] filelist = directory.listFiles();
		String model = "";
		for (int i = 0; i < filelist.length; i++) {
			String[] path = filelist[i].getAbsolutePath().split("\\\\");


			model = "insert into sense_agro_crops_test (crop_name,parent_id) select '"+path[2]+"',2 from dual where not exists (select cp2.crop_name from sense_agro_crops_test cp2 where cp2.crop_name = '"+path[2]+"');";

			//	System.out.println(filelist[i].getAbsolutePath());
//			model = "insert into sense_agro_crops (crop_name,parent_id)values(" + "'" + path[2] + "'," + 2 + ");";

			System.out.println(model);
			FileOutputStream fos = null;
			File file = new File("d:/old/dirsScan20190423.sql");
			if (!file.exists()) {
				file.createNewFile();
				fos = new FileOutputStream(file);
			}
			fos = new FileOutputStream(file, true);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");// 指定以UTF-8格式写入文件
			osw.write(model);
			osw.write("\r\n");
			osw.close();

		}
	}


}
