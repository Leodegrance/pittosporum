package com.pittosporum.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import sun.net.www.ParseUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.Files;

/**
 * @author yichen
 * @Date:2020/7/22
 */

@Slf4j
public class ResourceUtil {
	private static final String DEFAULT_TMP_DIR = "resourceTempFolder";
	public  static  final String DEFAULT_TMP_DIR_PATH;

	public static final String FILE_SEPARATOR  = System.getProperty("file.separator");
	static{
		String osDir = System.getProperty("java.io.tmpdir");
		if (!osDir.endsWith(FILE_SEPARATOR)) {
			osDir += FILE_SEPARATOR;
		}
		DEFAULT_TMP_DIR_PATH = osDir + DEFAULT_TMP_DIR;
	}

	public static void copyFileToDir(String folderName, String fileName) throws IOException {
		if (StringUtils.isEmpty(folderName) || StringUtils.isEmpty(fileName)){
			return;
		}

		String filePath = folderName + "/" + fileName;

		log.info("input stream " + filePath);

		InputStream path = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);

		log.info("input stream" + path);

		File dir = generateFolderInTempFolder(DEFAULT_TMP_DIR);

		String dirPath = dir.getAbsolutePath();

		File folder = new File(dirPath + "/" + folderName);
		if (!folder.exists()){
			folder.mkdirs();
		}

		log.info("folder.getAbsolutePath()" + folder.getAbsolutePath());
		File file = new File(folder , fileName);
		if (!file.exists()){
			boolean create = file.createNewFile();
			if(!create){
				log.info("create file is no success.");
			}
		}

		copyInputStreamToFile(path, file);
	}

	public static InputStream readFromJar(String jarPath, String file) throws IOException {
		JarURLConnection jarURLConnection=null;
		InputStream inputStream;
		try {
			URL fileUrl= ParseUtil.fileToEncodedURL(new File(jarPath));
			URL jarUrl = new URL("jar", "", -1, fileUrl + "!/");
			URL moduleUrl = new URL(jarUrl, ParseUtil.encodePath(file, false));
			jarURLConnection = (JarURLConnection)moduleUrl.openConnection();
			inputStream = jarURLConnection.getInputStream();
		} catch (IOException e) {
			throw e;
		} finally {
			if (jarURLConnection!=null){
				try {
					jarURLConnection.getJarFile().close();
				} catch (IOException ignore) {
					throw ignore;
				}
			}
		}

		return inputStream;
	}

	private static void copyInputStreamToFile(InputStream inputStream, File file)
			throws IOException {
		try (OutputStream  outputStream = Files.newOutputStream(file.toPath())) {
			FileCopyUtils.copy(inputStream, outputStream);
		}
	}

	public static InputStream resourceLoader(String fileFullPath) throws IOException {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		return resourceLoader.getResource(fileFullPath).getInputStream();
	}


	private static File generateFolderInTempFolder(String subFolder) {
		if (StringUtils.isEmpty(subFolder)) {
			return null;
		}

		File file = null;
		if (subFolder.matches("[0-9a-zA-Z_\\\\/-]+")) {
			String tempPath = System.getProperty("java.io.tmpdir");
			if (!tempPath.endsWith(FILE_SEPARATOR)) {
				tempPath += FILE_SEPARATOR;
			}
			if (subFolder.startsWith("/") || subFolder.startsWith("\\")) {
				subFolder = subFolder.substring(1);
			}
			file = new File(tempPath, subFolder);
			if (!file.exists()){
				file.mkdirs();
			}
		}

		return file;
	}
}
