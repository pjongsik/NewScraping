package scrap;

import java.io.File;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import Log.Log;
import message.TelegramMessage;
import process.DaumNew;
import process.PpumProcess;
import process.WebChecker;
import scheduler.Scheduler;
import scrap.model.News;
import scrap.model.Ppum;

public class Main {

	public static void main(String[] args) throws Exception {
		
		// iquest web checker
		//allWebChecker();
		//PrintStream outStream = new PrintStream(new File("outFile.txt"));
		//System.setOut(outStream);
		
		
		// daum news
		daumNewsSearch(30);
		
		// ppum
		int pageCount = 5;
		boolean oversea = true;
		boolean searchAfterlastTime = false;
		//ppumSearch(pageCount, oversea == false, searchAfterlastTime);
		//ppumSearch(pageCount, oversea, searchAfterlastTime == true);
	
		
		//String text = ScrapJsoup.parseHTMLData3();
		
		System.out.println("test");
		
		
	
	}
	
	
	private static void dracoSearch() {
		
		String text = Scraping.Scrap("https://www.mir4draco.com/price");
		
		System.out.println(text);
	}

	/**
	 * 뽐뿌확인
	 */
	private static void ppumSearch(int pageCount, boolean isOversee, boolean searchAfterLastTime) {
		
		List<Ppum> list = PpumProcess.pageScraping(pageCount, isOversee, searchAfterLastTime);
		
		String[] keywords = Keywords.getPpum();
		
		System.out.println("");
		System.out.println("");
		
		for (Ppum data : list) {
			System.out.println(data.toString());
		}
		
		System.out.println("===> keyword check <===");
		Log.write("===> keyword check <===");
		
		for (Ppum data : list) {
			//System.out.println(data.toString());
			Log.write(data.toString());
			for (String keyword : keywords) {
				if (data.getSubject().contains(keyword)) {
					
					Log.write("keyword : " + keyword);
					Log.write(data.toString());
					System.out.println(">>>> [keyword : " + keyword + "] --> " + data.toString());
					
					TelegramMessage.send(data.toString());
				}
			}
		}
	}

	/**
	 * 홈페이지 확인
	 */
	private static void allWebChecker() {
		
		// 확인 간격 10분
		int tenMinate = 1000 * 60 * 10;
		
		try {
			while(true) {
				WebChecker.allWebProcess();
				Thread.sleep(tenMinate);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.write(e.getMessage());
		}
	}

	
	private static void ppomChecker() {
		
		int tenMinate = 1000 * 60 * 10;
		
		try {
			while(true) {
				
				ppumSearch(1, false, false);
				ppumSearch(1, true, false);
				
				Thread.sleep(tenMinate);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.write(e.getMessage());
		}
	}
	
	
	/**
	 * 배치용
	 * @param args
	 * @throws Exception
	 */
	private static void newsScraping(String[] args) throws Exception {
		
		if (args.length == 0) {
			System.out.println("실행 시간 파라미터가 누락되었습니다. ex) 0830 ");
			return;
		}
	
		// 확인 간격
		int tenMinate = 1000 * 60 * 10;
		
		try {
			while(true) {
				
				for (String time : args) {
					
					if (Scheduler.isRunTime(time))
						daumNewsSearch(10);
				}
				
				Thread.sleep(tenMinate);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.write(e.getMessage());
		}
		
	}
	
	private static void daumNewsSearch(int pageCount) {
		
		if (pageCount == 0)
			 pageCount = 10;
		
		List<News> list = DaumNew.EconomicNewScraping(pageCount);
		
		String[] keywords = Keywords.get();
		
		Log.write("===> keyword check <===");
		
		for (News data : list) {
			
			for (String keyword : keywords) {
				if (data.getTitle().contains(keyword)) {
					
					Log.write("keyword : " + keyword);
					Log.write(data.toString());
					System.out.println("[keyword : " + keyword + "] --> " + data.toString());
					
					//TelegramMessage.send(data.toString());
				}
			}
		}
	}

}
