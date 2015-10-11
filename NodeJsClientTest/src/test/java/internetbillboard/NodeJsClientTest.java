package internetbillboard;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests nodejs server with 3 http clients at the same time. Each client sends 100 requests.
 *
 */
public class NodeJsClientTest {

	private static final int THREAD_COUNT = 3;

	List<NodeJsClientTestThread> threads;
	
	@Before
	public void setUp() {
		threads = new ArrayList<NodeJsClientTestThread>();
		for (int i = 0; i < THREAD_COUNT; i++) {
			NodeJsClientTestThread t = new NodeJsClientTestThread();
			HttpClient client = HttpClientBuilder.create().build();
			t.setClient(client);
			t.setName("t" + i);
			threads.add(t);
		}
	}
	
	@Test
	public void test() throws ClientProtocolException, IOException, InterruptedException, ParseException {
	
		/* read 'count' value first (from database). */
		
		HttpClient client = HttpClientBuilder.create().build();
		GetCountThread t = new GetCountThread();
		t .setClient(client);
		t.start();
		Thread.sleep(100);
		int count = readCount();
		
		
		/* run threads. */
		
		long start = System.currentTimeMillis();
		for (int i = 0; i < THREAD_COUNT; i++) {
			threads.get(i).start();
		}
		for (int i = 0; i < THREAD_COUNT; i++) {
			threads.get(i).join();
		}
		long time = (System.currentTimeMillis() - start);
		System.out.println("time: " + time);
		
		System.out.println(String.format("average response time: %f ms.", (double)time / (THREAD_COUNT * NodeJsClientTestThread.REQUEST_COUNT)));
		
		
		/* read 'count' value again and compare. */
		
		t = new GetCountThread();
		t .setClient(client);
		t.start();
		Thread.sleep(100);
		int count2 = readCount();
		
		assertEquals(count2 - count, 14850);
		
		System.out.println("Finished.");
		
	}

	private int readCount() throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		JSONObject o = (JSONObject) parser.parse(new FileReader("../count_value.json"));
		return Integer.parseInt((String) o.get("count_value"));
	
	}
	
	

}
