package mySpider;

import java.io.*;

public class Main  {

	public static void main(String[] args) throws IOException, InterruptedException {
		PrintWriter myFiles = new PrintWriter("D:\\mySpider.txt");
		String url = "https://book.douban.com/subject/26716975/";
		MySpider spider = new MySpider();
		while (true) {
			Thread.sleep(5000);
			spider.SendGet(url);
			String headMessage = spider.RegexString("<span property=\"v:itemreviewed\".*?<\\/h1>");
			String author = spider.RegexString("<a class=\"\" href=\"\\/search.*?<\\/a>");
			String mainMessage = spider.RegexString( "<div class=\"intro\".*?<\\/div>");
			String socre = spider.RegexString("<strong class=\"ll rating.*?<\\/strong>");
			headMessage = spider.clearMessage(headMessage);
			mainMessage = spider.clearMessage(mainMessage);
			author = spider.clearMessage(author);
			socre = spider.clearMessage(socre);
			System.out.println(headMessage + "�����ɹ�");
			myFiles.append("����:" + headMessage + "\r\n");
			myFiles.append("����:" + author + "\r\n");
			myFiles.append("���:" + mainMessage + "\r\n");
			myFiles.append("��������:" + socre + "\r\n");
			myFiles.append("����:" + url + "\r\n\r\n");
			myFiles.flush();
			url = spider.nextHtml("https:\\/\\/book.douban.com\\/subject\\/[0-9]{8}\\/");
			System.out.println(url);
		}
	}
}
