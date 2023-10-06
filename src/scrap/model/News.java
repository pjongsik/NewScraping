package scrap.model;

public class News {
	
	public News(String title, String url, String from, String time) {
		this.title = title;
		this.url = url;
		this.from = from;
		this.time = time;
	}
	
	public String title;
    public String url;
    public String from;
    public String time;
	public String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	@Override
	public String toString(){
		if (keyword == null) {
			return String.format("%s : %s [%s] - %s", title, from, time, url);
		}else {
			return String.format(" keyword [%s] --->  %s : %s [%s] - %s", keyword, title, from, time, url);
		}
	}
    
    
}
