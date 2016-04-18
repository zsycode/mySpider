package mySpider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Iterator;

public class MySpider {
	// 存储访问过的URL
	static private HashSet<String> myUrl = new HashSet<>();
	private String result;

	// 清除内容中的HTML标签
	public String clearMessage(String a) {
		Pattern pattern = Pattern.compile("<.*?>");
		Matcher matcher = pattern.matcher(a);
		while (matcher.find()) {
			a = matcher.replaceFirst("");
			matcher = pattern.matcher(a);
		}
		return a;
	}

	// 下载页面内容
	public void SendGet(String myurl) {
		String url = myurl;
		myUrl.add(url);
		BufferedReader in = null;
		try {
			result = "";
			URL realUrl = new URL(url); // 用获得的URL新建一个URL对象
			URLConnection connection = realUrl.openConnection(); // 新建一个URLconnection对象
			// 将User-Agent设为某浏览器标识，据说可以防止反爬，可我觉得没什么用
			connection.setRequestProperty("User-Agent",
					"User-Agent:Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50");
			connection.connect();
			// 新建一个BufferedReader对象,并将字符编码设为utf-8
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line;
			// 将所有内容存入result
			while ((line = in.readLine()) != null) {
				result += line;
			}

		} catch (Exception a) {
			a.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	// 找到符合正则表达式的语句
	public String RegexString(String patternStr) throws IOException {
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(result);
		if (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

	// 找到下一个URL
	public String nextHtml(String patternStr) {
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(result);
		if (matcher.find()) {
			while (myUrl.contains(matcher.group(0))) {
				if (matcher.find()) {
				} else {
					return findNewUrl(patternStr);
				}
			}
		} else {
			return findNewUrl(patternStr);
		}
		return matcher.group(0);
	}

	public String findNewUrl(String patternStr) {
		Iterator<String> i = myUrl.iterator();
		while (i.hasNext()) {
			result = i.next();
			return nextHtml(patternStr);
		}
		System.out.println("找不到未访问过的Url!");
		System.exit(-1);
		return "";
	}
}
