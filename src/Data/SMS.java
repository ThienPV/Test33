package Data;

public class SMS {
	private String id, content;

	public SMS(String id, String content) {
		super();
		this.id = id;
		this.content = content;
	}

	public SMS() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
