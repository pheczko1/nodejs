package internetbillboard;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

public class NodeJsClientTestThread extends Thread {

	private static final String USER_AGENT = "USER_AGENT";
	public static final int REQUEST_COUNT = 100;
	HttpClient client;
	
	public HttpClient getClient() {
		return client;
	}

	public void setClient(HttpClient client) {
		this.client = client;
	}

	@Override
	public void run() {
		for (int i = 0; i < REQUEST_COUNT; i++) {
			try {
				String url = URLBuilder.buildTrackURl(i, getName());
				sendRequest(client, url);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected void sendRequest(HttpClient client, String url) throws ClientProtocolException, IOException {

		HttpGet request = new HttpGet(url);

		// add request header
		request.addHeader("User-Agent", USER_AGENT);
		HttpResponse response = client.execute(request);

		
		int code = response.getStatusLine().getStatusCode();
		System.out.println(getName() + ": Response Code : " 
	                + code );
		
		assertEquals(code, 200);

		BufferedReader rd = new BufferedReader(
			new InputStreamReader(response.getEntity().getContent()));

		String line = "";
		while ((line = rd.readLine()) != null) {
/*			assertTrue(line.contains(ATTRIBUTES[0]));
			assertTrue(line.contains(ATTRIBUTES[1]));
			assertTrue(line.contains(ATTRIBUTES[2]));
*/			System.out.println(line);
		}
	}
	
}
