package sogouSpider.doSpider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sogouSpider.entity.Article;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.URL;
import java.net.URLConnection;
import com.alibaba.fastjson.JSON;

/**
 * @ author ezra
 * @ date 2019/4/17 15:39
 */
public class Crawler {


		Logger log = LoggerFactory.getLogger(Crawler.class);

		public String doCrawler(String url) {
			return dealPage(getPage(url));
		}

		/**
		 * 读取整个网页
		 *
		 * @param url:网页地址
		 * @return 整个网页的内容
		 */
		private String getPage(String url) {
			StringBuffer buffer = new StringBuffer();
			BufferedReader reader = null;
			try {
				URL realUrl = new URL(url);
				URLConnection conn = realUrl.openConnection();
				conn.connect();
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String line = null;
				while ((line = reader.readLine()) != null) {
					buffer.append(line).append("\r\n");
				}
				System.out.println(buffer);
				log.info("buffer={}",buffer);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("read web page error : " + e.getMessage());
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("close reader error : " + e.getMessage());
				}
			}
			return buffer.toString();
		}

		/**
		 * 对读取到的网页进行处理截取有用信息
		 *
		 * @param page
		 *            网页内容
		 * @return 截取到的信息
		 */
		private String dealPage(String page) {

			//所有的正则表达式
			Pattern[] patterns = new Pattern[4];
			patterns[0] = Pattern.compile("<p><img src=\"[a-zA-z]+://[^\\s]*");
			patterns[1] = Pattern.compile("<h4><a uigs=.*</a></h4>");
			patterns[2] = Pattern.compile("<img src=.*\" onload=\"vrImgLoad\\(this,'fit',110,110\\)\"");
			patterns[3] = Pattern.compile("阅读&nbsp;.*</bb>");

			//首先计算有多少篇文章
			int articleCount = 0;
			Matcher matcher = patterns[0].matcher(page);
			while (matcher.find()) {
				articleCount++;
			}

			//根据文章数量创建相应长度的数组
			Article[] articles = new Article[articleCount];
			for (int i = 0; i < articleCount; i++) {
				articles[i] = new Article();
			}

			//开始处理
			int index = 0;
			for (int i = 0; i < patterns.length; i++) {

				index = 0;
				matcher = patterns[i].matcher(page);
				while (matcher.find()) {
					String find = matcher.group();
					if(i==0){
						String[] splits = find.split("\"");
						if(index<articleCount){
							articles[index].setHeadImg(splits[1]);
						}
					}else if (i == 1){
						String[] splits = find.split("\"");
						if(index<articleCount){
							articles[index].setArticleUrl(splits[3]);
							articles[index].setTopic(splits[6].substring(1,splits[6].length()-9));
						}
					}else if (i == 2){
						String[] splits = find.split("\"");
						if(index<articleCount){
							articles[index].setArticleImg(splits[1]);
						}
					}else if (i == 3){
						String[] splits = find.split("&nbsp;");
						if(index<articleCount){
							articles[index].setReadCount(splits[1]);
							articles[index].setTime(splits[4].substring(13, 23));
						}
					}
					index++;
				}
			}
			return JSON.toJSONString(articles);
		}

		public static void main(String[] args) throws Exception {
			String url = "https://weixin.sogou.com/weixin?type=2&query=";
			String url2 =  "https://weixin.sogou.com";
			String keyword ="柑橘黄龙病";
			Crawler crawler = new Crawler();
		//	System.out.println(crawler.doCrawler(url+keyword));
			System.out.println(crawler.doCrawler(url2));
		}

}
