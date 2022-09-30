package process;

import java.util.ArrayList;
import java.util.List;

import scrap.Scraping;
import scrap.model.Ppum;

public class PpumProcess {
	
	 static List<Ppum> _list = new ArrayList<Ppum>();

	 public static List<Ppum> pageScraping(int endPage, boolean isOversee, boolean searchAfterLastTime)
     {
         // 국내
         String url = "https://www.ppomppu.co.kr/zboard/zboard.php?id=ppomppu";

         // 해외
         if (isOversee)
             url = "https://www.ppomppu.co.kr/zboard/zboard.php?id=ppomppu4";

         int page = 1;
        
         _list = null;
         _list = new ArrayList<Ppum>();

         while (page <= endPage)
         {
             if (page > 1)
                 url = String.format("%s&page=%s", url, page);

             // 한글셋
             Scraping.setCharset("EUC-KR");
             String text = Scraping.Scrap(url);
             //System.out.println(text);
             _list.addAll(getPpumData(text));

             page++;
         }

         // 마지막 조회시간확인
         //String lasttime = GetLastSearchTime(isOversee);
         //SetLastSearchTime(isOversee);

        
        // if (string.IsNullOrEmpty(lasttime) == false && searchAfterLastTime)
        //     _list = _list.Where(x => x.시간.CompareTo(lasttime) > 0).ToList();

         return _list;
     }
	
	public static List<Ppum> getPpumData(String text) {
		
		  final String hot_icon2 = "/images/menu/not_icon2.jpg";    // hot
          final String pop_icon2 = "/images/menu/pop_icon2.jpg";    // 인기

          // 제목
          final String startText = "<font class=list_title>";
          final String startText_closed = "<font color=#ACACAC>";
          final String endText = "</font>";

          // 등록시간
          final String startTime = "<td nowrap class='eng list_vspace' colspan=2  title=";
          final String endTime = "<nobr class='eng list_vspace'>";


          // 상세페이지 url
          final String startUrl = "<a href=";
          
          //
          String temp = text;
          String urlTemp = "";

          String bigo = "";

          boolean hot = false;
          boolean pop = false;
          boolean closed = false;

          List<Ppum> titleList = new ArrayList<Ppum>();
          while (temp.indexOf(startText) > 0 || temp.indexOf(startText_closed) > 0)
          {
              hot = pop = closed = false;
              bigo = "";

              int startTextIndex = temp.indexOf(startText);
              int closedTextIndex = temp.indexOf(startText_closed);

              //System.out.println("startTextIndex : " + String.valueOf(startTextIndex));
              //System.out.println("closedTextIndex : " + String.valueOf(closedTextIndex));
              
              String searchText = startText;
              if (startTextIndex < 0 || (closedTextIndex > 0 && closedTextIndex < startTextIndex))
              {
                  closed = true;
                  searchText = startText_closed;
              }

              //System.out.println("closed : " + String.valueOf(closed));
              //System.out.println("searchText : " + searchText);
              
              // url
              urlTemp = temp.substring(0, temp.indexOf(searchText) - 4);
              
              
              // 인기. 핫 확인
              if (urlTemp.indexOf(hot_icon2) > 0)
                  hot = true;
              else if (urlTemp.indexOf(pop_icon2) > 0)
                  pop = true;

              // 바로가기 URL만 남김
              urlTemp = urlTemp.substring(urlTemp.lastIndexOf(startUrl) + startUrl.length() + 1);
              
              //
              bigo = String.format("%s%s", closed ? "<종결>" : "", hot ? "[핫]" : pop ? "[인기]" : "");

              // 제목/시간
              temp = temp.substring(temp.indexOf(searchText) + searchText.length());
              titleList.add(new Ppum(bigo
                                          , temp.substring(0, temp.indexOf(endText))
                                          , String.format("%s%s", "https://www.ppomppu.co.kr/zboard/", urlTemp)
                                          , temp.substring(temp.indexOf(startTime) + startTime.length() + 1, (temp.indexOf(startTime) + startTime.length() + 1) + ((temp.indexOf(endTime) - 3) - (temp.indexOf(startTime) + startTime.length() + 1)))
                                          , hot, pop, closed));
              
          }

          return titleList;
		
	}
}
