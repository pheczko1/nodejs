package internetbillboard;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public class GetCountThread extends NodeJsClientTestThread {
	@Override
	public void run() {
		try {
			String url = URLBuilder.buildCountRequestURl();
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
