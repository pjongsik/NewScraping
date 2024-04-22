package scrap;

import Log.Log;
import process.DaumNew;
import process.NaverNew;
import process.PpumProcess;
import process.WebChecker;
import scheduler.Scheduler;
import scrap.model.News;
import scrap.model.Ppum;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws Exception {
		
		try {
			// iquest web checker
			//allWebChecker();
			//PrintStream outStream = new PrintStream(new File("outFile.txt"));
			//System.setOut(outStream);
			
			// daum news	
			//daumNewsSearch(30);
			//
			//naver news
			//naverNewsSearch(30);

			int pageCount = 20;
			boolean oversea = true;
			boolean searchAfterlastTime = false;
		 	ppumSearch(pageCount, oversea == false, searchAfterlastTime);
			ppumSearch(pageCount-13, oversea, searchAfterlastTime == true);

			//String text = ScrapJsoup.parseHTMLData3();
			 
			System.out.println("the end");
		} catch (Exception ex) {
		
			System.out.println(ex.getStackTrace()[0]);
			System.out.println(ex.getMessage() );
			//ex.printStackTrace();
			
		}
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

		List<Ppum> selectedList = new ArrayList<Ppum>();
		for (Ppum data : list) {
			//System.out.println(data.toString());
			Log.write(data.toString());
			for (String keyword : keywords) {
				if (data.getSubject().toLowerCase().contains(keyword)) {

					if (selectedList.stream().filter(x -> x.getSubject().equals(data.getSubject())).count() > 0) {
						selectedList.stream().filter(x -> x.getSubject().equals(data.getSubject())).forEach(x -> x.setKeyword(x.getKeyword() + ", " + keyword));
					} else {
						data.setKeyword(keyword);
						selectedList.add(data);
					}
					//TelegramMessage.send(data.toString());
					//
				}
			}
		}

		for (Ppum data : selectedList) {

			//Log.write("keyword : " + keyword);
			//Log.write(data.toString());
			System.out.println("--> " + data.toString());

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

		Log.write("");
		Log.write("");
		Log.write("===> keyword check <===");

		List<News> selectedList = new ArrayList<News>();
		String pre_title = ""; // 중복 방지

		for (News data : list) {
			
			if (data.title.equals(pre_title))
				continue;
			
			pre_title = data.title;
			for (String keyword : keywords) {
				if (data.getTitle().contains(keyword)) {

					if (selectedList.stream().filter(x -> x.getTitle().equals(data.getTitle())).count() > 0) {
						selectedList.stream().filter(x -> x.getTitle().equals(data.getTitle())).forEach(x -> x.setKeyword(x.getKeyword() + ", " + keyword));
					} else {
						data.setKeyword(keyword);
						selectedList.add(data);
					}

					//TelegramMessage.send(data.toString());
				}
			}
		}

		for (News data : selectedList) {
			System.out.println(data.toString());
		}
	}

	

	private static void naverNewsSearch(int pageCount) throws Exception {
		
		if (pageCount == 0)
			 pageCount = 10;

		//https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1=101#&date=%2000:00:00&page=2

		List<News> list = NaverNew.Scraping("LSD", "shm", "101", "", "", pageCount);
		
		String[] keywords = Keywords.get();

		Log.write("");
		Log.write("");
		Log.write("===> keyword check <===");

		List<News> selectedList = new ArrayList<News>();
		String pre_title = ""; // 중복 방지
		for (News data : list) {
			
			if (data.title.equals(pre_title))
				continue;
			
			pre_title = data.title;


			for (String keyword : keywords) {

				if (data.getTitle().contains(keyword)) {

					if (selectedList.stream().filter(x -> x.getTitle().equals(data.getTitle())).count() > 0) {
						selectedList.stream().filter(x -> x.getTitle().equals(data.getTitle())).forEach(x -> x.setKeyword(x.getKeyword() + ", " + keyword));
					} else {
						data.setKeyword(keyword);
						selectedList.add(data);
					}
					//Log.write("keyword : " + keyword);
					//Log.write(data.toString());
					//System.out.println("[keyword : " + keyword + "] --> " + data.toString());
					
					//TelegramMessage.send(data.toString());
				}
			}
		}

		for (News data : selectedList) {
			System.out.println(data.toString());
		}
	}

	
}
