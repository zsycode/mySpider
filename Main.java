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
			System.out.println(headMessage + "检索成功");
			myFiles.append("标题:" + headMessage + "\r\n");
			myFiles.append("作者:" + author + "\r\n");
			myFiles.append("简介:" + mainMessage + "\r\n");
			myFiles.append("豆瓣评分:" + socre + "\r\n");
			myFiles.append("链接:" + url + "\r\n\r\n");
			myFiles.flush();
			url = spider.nextHtml("https:\\/\\/book.douban.com\\/subject\\/[0-9]{8}\\/");
			System.out.println(url);
		}
	}
}
