package simple.province.spider;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;

/**
 * @ author ezra
 * @ date 2019/4/17 17:35
 */

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
//使用代理ip。项目不行
public class IpProxy {



		public static void main(String[] args) throws MalformedURLException, IOException {
			// 使用java.net.Proxy类设置代理IP 114.243.67.243:8118
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("114.215.102.168", 8081));
			HttpURLConnection connection = (HttpURLConnection)new URL("http://www.baidu.com/").openConnection(proxy);
			connection.setConnectTimeout(6000); // 6s
			connection.setReadTimeout(6000);
			connection.setUseCaches(false);

			if(connection.getResponseCode() == 200){
				System.out.println("代理ip连接成功");
			}
		}


	}
