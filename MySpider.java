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
	// �洢���ʹ���URL
	static private HashSet<String> myUrl = new HashSet<>();
	private String result;

	// ��������е�HTML��ǩ
	public String clearMessage(String a) {
		Pattern pattern = Pattern.compile("<.*?>");
		Matcher matcher = pattern.matcher(a);
		while (matcher.find()) {
			a = matcher.replaceFirst("");
			matcher = pattern.matcher(a);
		}
		return a;
	}

	// ����ҳ������
	public void SendGet(String myurl) {
		String url = myurl;
		myUrl.add(url);
		BufferedReader in = null;
		try {
			result = "";
			URL realUrl = new URL(url); // �û�õ�URL�½�һ��URL����
			URLConnection connection = realUrl.openConnection(); // �½�һ��URLconnection����
			// ��User-Agent��Ϊĳ�������ʶ����˵���Է�ֹ���������Ҿ���ûʲô��
			connection.setRequestProperty("User-Agent",
					"User-Agent:Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50");
			connection.connect();
			// �½�һ��BufferedReader����,�����ַ�������Ϊutf-8
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line;
			// ���������ݴ���result
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

	// �ҵ�����������ʽ�����
	public String RegexString(String patternStr) throws IOException {
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(result);
		if (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

	// �ҵ���һ��URL
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
		System.out.println("�Ҳ���δ���ʹ���Url!");
		System.exit(-1);
		return "";
	}
}
