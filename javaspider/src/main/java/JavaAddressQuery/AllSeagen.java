package JavaAddressQuery;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @ author ezra
 * @ date 2019/4/24 16:48
 */
public class AllSeagen {
	public static void main(String[] args) throws IOException {
		HttpClient client = HttpClients.createDefault();// 创建默认http连接
		HttpPost post = new HttpPost("http://api.map.baidu.com/geocoder/v2/");// 创建一个post请求
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("ak", "vGLMdVamgBzo2tmGbzsdN2KGsyZfvuOP"));//传递的参数
		paramList.add(new BasicNameValuePair("sn", "b59c626b2eda2bff23ed57664060c70e"));//传递的参数
		paramList.add(new BasicNameValuePair("location", "24.2606,114.71447"));//传递的参数
		// 把参转码后放入请求实体中
		HttpEntity entitya = new UrlEncodedFormEntity(paramList, "utf-8");
		post.setEntity(entitya);// 把请求实体放post请求中
		HttpResponse response = client.execute(post);// 用http连接去执行get请求并且获得http响应
		HttpEntity entity = response.getEntity();// 从response中取到响实体
		System.out.println("entity.getContent()"+entity.getContent());
		System.out.println("entity:getContentEncoding():"+entity.getContentEncoding());
		System.out.println("entity:getContentLength():"+entity.getContentLength());
		System.out.println("entity:getContentType():"+entity.getContentType());
		String html = EntityUtils.toString(entity);// 把响应实体转成文本
		System.out.println(html.substring(html.indexOf("<formatted_address>")+"<formatted_address>".length(),html.indexOf("</formatted_address>")));
		System.out.println(html);


	}
}

