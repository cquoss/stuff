package de.quoss.examples.httpexample;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpClientExample {

	private static final String CLASSNAME = HttpClientExample.class.getName();

	private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

	private static final String FILENAME = "test.txt";

	private static final String REQUEST_METHOD_POST = "POST";

	private static final String REQUEST_METHOD_GET = "GET";

	private HttpClientExample() throws Exception {
		super();
		String urlString = String.format("http://localhost:8080/test?filename=%s", FILENAME);
		// String urlString = "http://localhost:8080/test";
		String charset = "UTF-8";
		URL url = new URL(urlString);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setRequestMethod(REQUEST_METHOD_POST);
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestProperty("Accept-Charset", charset);
		httpURLConnection.setRequestProperty("Content-Type", "text/plain");
		InputStream inputStream = new FileInputStream("input\\".concat(FILENAME));
		byte[] b = new byte[1024];
		int bytesRead;
		int overallBytesRead = 0;
		StringBuilder stringBuilder = new StringBuilder();
		while ((bytesRead = inputStream.read(b)) != -1) {
			stringBuilder.append(new String(b).substring(0, bytesRead));
			overallBytesRead += bytesRead;
		}
		httpURLConnection.setRequestProperty("Content-Length", Integer.toString(overallBytesRead));
		OutputStream outputStream = httpURLConnection.getOutputStream();
		outputStream.write(stringBuilder.toString().getBytes());
		outputStream.close();
		int responseCode = httpURLConnection.getResponseCode();
		LOGGER.log(Level.INFO, "[responseCode={0}]", new Object[] { responseCode });
		inputStream = httpURLConnection.getInputStream();
		int contentLength = httpURLConnection.getContentLength();
		bytesRead = inputStream.read(b);
		while ((bytesRead = inputStream.read(b)) != -1) {
			LOGGER.log(Level.INFO, new String(b).substring(0, bytesRead));
		}
		inputStream.close();
		httpURLConnection.disconnect();
	}

	public static void main(String[] args) throws Exception {
		new HttpClientExample();
	}

}
