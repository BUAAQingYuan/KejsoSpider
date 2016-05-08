package cn.kejso.Tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FileDownloadUtil {

	public static String download(String pageUrl, String urlString, String savePath) throws IOException {

		return download(pageUrl, urlString, savePath, null);
	}

	public static String download(String pageUrl, String urlString, String savePath, String ext) throws IOException {

		File sf = new File(savePath);
		if (!sf.exists()) {
			sf.mkdirs();
		}

		String filename = getFileName(urlString, ext);
		OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);

		String realUrlString = getUrlString(pageUrl, urlString);
		URL url = new URL(realUrlString);
		URLConnection con = url.openConnection();
		con.setConnectTimeout(5000);
		InputStream is = con.getInputStream();

		byte[] bs = new byte[1024];
		int len;
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}

		os.close();
		is.close();

		return filename;
	}

	private static String getUrlString(String pageUrl, String urlString) {
		
		if (!urlString.startsWith("http://")) {
			return pageUrl.substring(0, pageUrl.lastIndexOf('/') + 1) + urlString;
		}
		else 
			return urlString;
	}

	private static String getFileName(String urlString, String ext) {
		if (ext != null && !ext.isEmpty())
			return FileDownloadUtil.genName() + "." + ext;
		else
			return FileDownloadUtil.genName() + FileDownloadUtil.getExt(urlString);
	}

	private static String genName() {

		return String.format("%s%04d", new SimpleDateFormat("yyyyMMddHHmmss_").format(new Date()),
				new Random().nextInt(10000));
	}

	private static String getExt(String url) {

		String ext = new String();

		String lastStr = url.substring(url.lastIndexOf('/') + 1, url.length());
		int quePos = lastStr.indexOf('?');
		if (quePos >= 0) {
			lastStr = lastStr.substring(0, quePos);
		}

		List<String> piece = Arrays.asList(lastStr.split("\\."));

		if (piece.size() > 1) {
			ext = "." + piece.get(piece.size() - 1);
		}

		return ext;
	}
}
