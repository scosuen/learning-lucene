package com.ying.learning_lucene.framework2;

public class RAMDocument {

	private String id;
	private String title;
	private String body;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "id: " + getId() + ", title:" + getTitle() + ", body:" + getBody();
	}
}
