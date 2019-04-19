package simple.province.spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

/**
 * @ author ezra
 * @ date 2019/4/12 18:16
 */
public class Html2 {

		//根据url从网络获取网页文本
		public Document getHtmlTextByUrl(String url) {
        /*
        1.Document:A HTML Document.
         */
			Document doc = null;
			try {
				int i = (int) (Math.random() * 1000); //做一个随机延时，防止网站屏蔽
				while (i != 0) {
					i--;
				}
            /*
            1.The core public access point to the jsoup functionality.【访问jsoup 功能的核心开放接口】
            2.connect(url) -> 连接到某个url上的方法
            3.data():Add a request data parameter
            4.cookie():Set a cookie to be sent in the request
            5.post():Execute the request as a POST, and parse the result.
            */
				doc = Jsoup.connect(url).data("query", "Java")
						.userAgent("Mozilla").cookie("auth", "token")
						.timeout(300000).post();
			} catch (IOException e) {
				e.printStackTrace();
				try {
					doc = Jsoup.connect(url).timeout(5000000).get();
				} catch (IOException e1) { // TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			return doc;
		}

		//get html by local url,if local url not exists ,get url through network and save in local
		public Document getHtmlTextByPath(String name, String url) {
			String path = "D:/" + name + ".htm"; //the local url's path
			Document doc = null;
			File input = new File(path);
			try {
            /*
            1.(optional) character set of file contents. Set to null to determine from http-equiv meta tag,
            if present, or fall back to UTF-8 (which is often safe to do).
             */
				doc = Jsoup.parse(input,"gb2312");//gb2312是后面url指定网站使用的一个CHARSET【这个值需要根据需要进行设置】
				if (!doc.children().isEmpty()) {
					//doc = null;
					System.out.println("已经存在");
				}
			} catch (IOException e) {
				System.out.println("文件未找到，正在从网络获取.......");
				doc = this.getHtmlTextByUrl(url);
				//并且保存到本地
				this.saveHtml(url,name);
			}
			return doc;
		}

		//将网页保存在本地（通过url,和保存的名字）
		public void saveHtml(String url,String name) {
			try {
				name = name + ".htm";//set file's name
				// System.out.print(name);
				File dest = new File("D:/" + name);//D:\
				//接收字节输入流
				InputStream is;
				//字节输出流
				FileOutputStream fos = new FileOutputStream(dest);
				URL temp = new URL(url);
				is = temp.openStream();
				//为字节输入流加缓冲
				BufferedInputStream bis = new BufferedInputStream(is);
				//为字节输出流加缓冲
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				int length;
				byte[] bytes = new byte[1024 * 20];
				while ((length = bis.read(bytes, 0, bytes.length)) != -1) {
					fos.write(bytes, 0, length);
				}
				bos.close();
				fos.close();
				bis.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		//根据元素属性获取某个元素内的elements列表
		public Elements getEleByClass(Document doc, String className)
		{
			Elements elements= null;
			elements = doc.select(className);//这里把我们获取到的html文本doc，和工具class名，注意<tr class="provincetr">
			return elements;   //此处返回的就是所有的tr集合
		}


		//获取省 、市 、县等的信息
		public ArrayList getProvince(String name, String url , String type)
		{
			ArrayList result= new ArrayList();

			// 标签定位
			String classType = "tr." +type;
			//"tr.provincetr"  => 要获取标签为<tr class="provincetr">的信息


			System.out.println("classType is :" + classType);
			//从网络上获取网页
			// Document doc = this.getHtmlTextByUrl(url);

			//从本地获取网页,如果没有则从网络获取
			Document doc2 = this.getHtmlTextByPath(name,url);
			System.out.println(name);

			if(doc2!=null){ //如果存在集合，则取出数据
				System.out.println("doc2!=null");
				Elements es =this.getEleByClass(doc2,classType);  //根据上述的classType得到tr的集合
				//System.out.println("es is :"+es.toString());
				for(Element e : es)   //依次循环每个元素，也就是一个tr
				{
					if(e!=null){
						//System.out.printf("element!=null"); //->for test
						for(Element ec : e.children())  //一个tr的子元素td，td内包含a标签
						{
							//身份的信息： 原来的url（当前url）  名称（北京） 现在url（也就是北京的url）  类型（prv）省
							String[] prv = new String[4];
							if(ec.children().first()!=null)
							{
								//原来的url
								prv[0]=url;  //就是参数url
                            /* 获取城市名
                            1.first()：Get the first matched element.
                            2.ownText(): Gets the text owned by this element only; does not get the combined text of all children.
                             */
								prv[1]=ec.children().first().ownText();  //a标签文本  如:北京
								System.out.println(prv[1]);

								/* 获取子url地址
								 */
								prv[2]=ec.children().first().attr("abs:href");  //北京的url
								System.out.println(prv[2]);

                            /*级别
                            prv[3]=type; //就是刚刚传的类型，后面会有city 、county等
                            result.add(prv);//将所有身份加入list中
                            */
							}
						}
					}
				}
			}
			//返回所有的省份信息集合，存数据库，字段类型为： baseurl  name ownurl levelname（type） updatetime
			return result;
		}
		public static void main(String[] args) {
			Html2 html = new Html2();
			//html.getHtmlTextByPath("2","http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2015/index.html");
			html.getProvince("2","http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2015/index.html","provincetr");
		}



}
