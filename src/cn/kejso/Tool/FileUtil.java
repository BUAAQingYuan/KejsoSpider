package cn.kejso.Tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	// 打印error url到文本文件
	public static void PrintURL(String file, String conent) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
			out.write(conent + "\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 获得error url
	public static List<String> ExcepUrls(String path) throws IOException {
		List<String> temp = new ArrayList<String>();

		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		while ((line = br.readLine()) != null) {
			temp.add(line);
			System.out.println(line);
		}
		br.close();
		fr.close();
		return temp;
	}

	// 将计数写入cache中
	// 每次一行
	public static void PrintPosition(int position, String file) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
			out.write(position + "\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	// 从cache中读取计数
	public static int ReadLastPosition(String file) throws NumberFormatException, IOException {
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		String lastline = null;
		String line = null;
		while ((line = br.readLine()) != null) {
			lastline = line;
		}

		int pos = Integer.valueOf(lastline);

		System.out.println(pos);

		br.close();
		fr.close();

		return pos;
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		FileUtil.PrintPosition(500, "MysqlCache/kaka");
		FileUtil.ReadLastPosition("MysqlCache/kaka");
	}

	public static boolean isFileExisted(String path) {

		File file = new File(path);
		return file.exists();
	}

	public static void PrintProxy(String path, List<String[]> proxys) {

		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true)));
			for (String[] content : proxys) {
				out.write(content[0] + ":" + content[1] + "\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static List<String[]> readProxy(String path) {

		List<String[]> temp = new ArrayList<String[]>();

		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			String line = "";
			while ((line = br.readLine()) != null) {
				if (!line.trim().isEmpty()) {
					temp.add(line.trim().split(":"));
				} else {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return temp;
	}
}
