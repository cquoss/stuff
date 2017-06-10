package de.quoss.example.httpexample;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * example how to send a http request and receive the response
 * 
 * @author Clemens Quoß
 *
 */
class HttpClientExample {

	/**
	 * classname
	 */
	private static final String CLASSNAME = HttpClientExample.class.getName();

	/**
	 * logger
	 */
	private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

	/**
	 * private working constructor
	 */
	private HttpClientExample() throws HttpExampleException {

		// start message
		LOGGER.log(Level.INFO, "start");

		// try to open http connection
		HttpURLConnection httpURLConnection = null;
		try {
			httpURLConnection = (HttpURLConnection) new URL("http://localhost:8080/?param1=value1&param2=value2").openConnection();
		} catch (IOException e) {
			throw new HttpExampleException(e);
		}

		// try to set GET request method
		try {
			httpURLConnection.setRequestMethod("GET");
		} catch (ProtocolException e) {
			throw new HttpExampleException(e);
		}
		
		// try to connect
		try  {
			httpURLConnection.connect();
		} catch (IOException e) {
			throw new HttpExampleException(e);
		}
		
		// try to log response code
		try {
			int responseCode = httpURLConnection.getResponseCode();
			LOGGER.log(Level.INFO, "Response code: {0}", new Object[] { responseCode });
		} catch (IOException e) {
			throw new HttpExampleException(e);
		}
		
		// try to log response message
		try {
			String responseMessage = httpURLConnection.getResponseMessage();
			LOGGER.log(Level.INFO, "Response message: {0}", new Object[] { responseMessage });
		} catch (IOException e) {
			throw new HttpExampleException(e);
		}
		
		// end message
		LOGGER.log(Level.INFO, "end");

	}

	public static void main(String[] args) {

		// try to call private working constructor
		try {
			new HttpClientExample();
		} catch (HttpExampleException e) {
			LOGGER.log(Level.SEVERE, "", e);
			System.exit(1);
		}

	}

}
