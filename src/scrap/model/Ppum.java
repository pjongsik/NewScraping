package scrap.model;

public class Ppum {
	
	public Ppum(String bigo, String subject, String time, String url,  boolean hot, boolean pop, boolean closed) {
		this.bigo = bigo;
		this.subject = subject;
		this.time = time;
		this.url = url;
		this.hot = hot;
		this.pop = pop;
		this.closed = closed;
	}
	
	public String bigo;
	public String subject;
	public String time;
	public String url;
	
	public boolean hot;
	public boolean pop;
	public boolean closed;
	public String getBigo() {
		return bigo;
	}
	public void setBigo(String bigo) {
		this.bigo = bigo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isHot() {
		return hot;
	}
	public void setHot(boolean hot) {
		this.hot = hot;
	}
	public boolean isPop() {
		return pop;
	}
	public void setPop(boolean pop) {
		this.pop = pop;
	}
	public boolean isClosed() {
		return closed;
	}
	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s %s - %s", closed ? "종결" : hot ? "핫핫" : pop ? "인기" : "", subject, time, url);
	}
}
