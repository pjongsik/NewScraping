
package process;

import java.util.ArrayList;
import java.util.List;

import Log.Log;
import scrap.Scraping;
import scrap.model.News;

/**
 * 최신 : https://news.daum.net/breakingnews/
 * 시회 : https://news.daum.net/breakingnews/society
 * 정치 : https://news.daum.net/breakingnews/politics
 * 경제 : https://news.daum.net/breakingnews/economic
 * 국제 : https://news.daum.net/breakingnews/foreign
 * 문화 : https://news.daum.net/breakingnews/culture
 * 연예 : https://news.daum.net/breakingnews/entertain
 * 스포츠 : https://news.daum.net/breakingnews/sports
 * IT : https://news.daum.net/breakingnews/digital
 * 칼럼 : https://news.daum.net/breakingnews/editorial
 * 보도자료 : https://news.daum.net/breakingnews/press
 * 자동생성 : https://news.daum.net/breakingnews/botnews
 * 
 * 날짜 조건 : ?regDate=20220530
 * @author PC211
 *
 */
public class DaumNew {
	
	public static List<News> EconomicNewScraping(int pageCount) {
		
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

            String url = String.format("https://news.daum.net/breakingnews/economic?page=%d", page);
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
