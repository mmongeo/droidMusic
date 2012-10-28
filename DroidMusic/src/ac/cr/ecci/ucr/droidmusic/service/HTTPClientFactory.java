package ac.cr.ecci.ucr.droidmusic.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;

public class HTTPClientFactory {

	/*
	 * Codigo del profesor Franklin Garcia del curso ci2354
	 * 2012
	 * */
	
	
	public static enum ClientType {
		HTTP_CLIENT, HTTP_URL_CONNECTION
	}

	public static InputStream getInputStream(String sourcePath, ClientType type)
			throws ClientProtocolException, IOException {
		switch (type) {
		case HTTP_CLIENT:
			return getInputStreamFromHttpClient(sourcePath);
		case HTTP_URL_CONNECTION:
			return getInputStreamFromUrlConnection(sourcePath);
		}
		return null;
	}

	private static InputStream getInputStreamFromUrlConnection(String sourcePath)
			throws IOException {

		URL url = new URL(sourcePath);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // url.openConnection(Proxy)

		int responseCode = connection.getResponseCode();
		if (responseCode == 200) {
			return connection.getInputStream();
		}
		return null;
	}

	private static InputStream getInputStreamFromHttpClient(String sourcePath)
			throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(sourcePath);
		HttpResponse response = client.execute(request);
		int responseCode = response.getStatusLine().getStatusCode();
		if (responseCode == 200) {
			HttpEntity entity = response.getEntity();
			return entity.getContent();
		}
		return null;
	}

	public static File getImage(Context context, String path)
			throws IOException {
		InputStream in = getInputStreamFromUrlConnection(path);
		URL url = new URL(path);
		String[] parts = url.getFile().split("/");
		String filename = parts[parts.length - 1];

		File parent = context.getExternalCacheDir();
		File outFile = new File(parent, filename);

		FileOutputStream fout = new FileOutputStream(outFile);

		int r = 0;
		while ((r = in.read()) != -1) {
			fout.write(r);
		}
		fout.close();
		in.close();

		return outFile;
	}
}