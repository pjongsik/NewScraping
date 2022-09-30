
package process;

import java.util.ArrayList;
import java.util.List;

import Log.Log;
import scrap.Scraping;
import scrap.model.News;

/*
 * 경제
https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1=101#&date=%2000:00:00&page=2

금융
https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid2=259&sid1=101&date=20220223&page=1

증권
https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid2=258&sid1=101&date=20220223&page=1

산업/재계
https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid2=261&sid1=101&date=20220223&page=2

중기/벤처
https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid2=771&sid1=101&date=20220223&page=2

부동산
https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid2=260&sid1=101&date=20220223&page=2

글로벌 경제
https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid2=262&sid1=101&date=20220223&page=2

생활경제
https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid2=310&sid1=101&date=20220223&page=2

경제 일반
https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid2=263&sid1=101&date=20220223&page=2

IT / 과학
https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1=105#&date=%2000:00:00&page=2

 */

public class NaverNew {
	
	public static List<News> Scraping(String mode, String mainId, String sub1Id, String sub2Id, String date, int pageCount) {
		
		List<News> news = new ArrayList<News>();

        int page = 1;

        String filter1 = "<strong class=\"tit_thumb\">";
        String filter2 = "<a href=\"";
        String filter2_1 = "\"";

        String filter3 = "class=\"link_txt\">";
        String filter3_1 = "</a>";

        String filter5 = "class=\"info_news\">";
        String filter5_1 = "<span class=\"txt_bar\"";

        String filter6 = "class=\"info_time\">";
        String filter6_1 = "</span>";
		

        while (page <= pageCount)
        {
            System.out.println("page : " + page);
            Log.write("page : " + page);

            // 소붐
            String url = String.format("https://news.naver.com/main/list.naver?mode=%s&mid=%s&sid2=%s&sid1=%s&date=%s&page=%d", page);
            // 대분류
            //url = String.format("https://news.naver.com/main/main.naver?mode=%s&mid=%s&sid1=%s#&date=%2000:00:00&page=%d", page);
            String text = Scraping.Scrap(url);
            while (text.indexOf(filter1) > 0)
            {
                text = text.substring(text.indexOf(filter1) + filter1.length());
                text = text.substring(text.indexOf(filter2) + filter2.length());

                //
                String clickUrl = text.substring(0, text.indexOf(filter2_1));

                text = text.substring(text.indexOf(filter3) + filter3.length());

                String title = text.substring(0, text.indexOf(filter3_1));

                // 출처, 시간이 없으면 pass~
                if (text.indexOf(filter5) < 0 || text.indexOf(filter6) < 0)
                    continue;

                text = text.substring(text.indexOf(filter5) + filter5.length());

                String from = text.substring(0, text.indexOf(filter5_1));

                text = text.substring(text.indexOf(filter6) + filter6.length());

                String time = text.substring(0, text.indexOf(filter6_1));

                news.add(new News(title, clickUrl, from, time));

                System.out.println(title);
                Log.write(title);
                System.out.println(clickUrl + " - from : " +from+ ", " + time);
                Log.write(clickUrl + " - from : " +from+ ", " + time);
            }

            page++;
        }
        
		return news;
		
	}

}
