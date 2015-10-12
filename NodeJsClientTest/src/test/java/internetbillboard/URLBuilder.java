package internetbillboard;

public class URLBuilder {

	private static final String[] ATTRIBUTES = {"url", "index" , "count"  };
	public static String buildTrackURl(int i, String name) {
		return String.format("http://localhost:8888/track?%s=http://%s/%d&%s=%s&%s=%d", 
				ATTRIBUTES[0], name, i, ATTRIBUTES[1] + i, name + i, ATTRIBUTES[2], i);
	}
	
	public static String buildCountRequestURl() {
		return "http://localhost:8888/get_count";
	}
}
