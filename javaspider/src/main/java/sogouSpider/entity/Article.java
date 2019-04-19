package sogouSpider.entity;

import java.io.Serializable;

/**
 * @ author ezra
 * @ date 2019/4/17 15:40
 */
public class Article implements Serializable {


		//头像图片
		private String headImg;
		//标题
		private String topic;
		//文章链接
		private String articleUrl;
		//文章图片
		private String articleImg;
		//阅读次数
		private String readCount;
		//时间戳
		private String time;

		public String getHeadImg() {
			return headImg;
		}

		public void setHeadImg(String headImg) {
			this.headImg = headImg;
		}

		public String getTopic() {
			return topic;
		}

		public void setTopic(String topic) {
			this.topic = topic;
		}

		public String getArticleUrl() {
			return articleUrl;
		}

		public void setArticleUrl(String articleUrl) {
			this.articleUrl = articleUrl;
		}

		public String getArticleImg() {
			return articleImg;
		}

		public void setArticleImg(String articleImg) {
			this.articleImg = articleImg;
		}

		public String getReadCount() {
			return readCount;
		}

		public void setReadCount(String readCount) {
			this.readCount = readCount;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;

		}
}
