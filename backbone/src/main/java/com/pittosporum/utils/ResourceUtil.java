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
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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

		InputStream path = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);

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

	private static void copyInputStreamToFile(InputStream inputStream, File file)
			throws IOException {
		try (OutputStream  outputStream = Files.newOutputStream(file.toPath())) {
			FileCopyUtils.copy(inputStream, outputStream);
		}
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

	public static Set<Class> findClassesByJar(String pathToJar) throws IOException, ClassNotFoundException {
		JarFile jarFile = new JarFile(pathToJar);
		Enumeration<JarEntry> e = jarFile.entries();

		URL[] urls = { new URL("jar:file:" + jarFile + "!/") };
		URLClassLoader cl = URLClassLoader.newInstance(urls);

		Set<Class> classes = new HashSet<>();
		while (e.hasMoreElements()) {
			JarEntry jarEntry = e.nextElement();
			if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class")) {
				continue;
			}

			// -6 because of .class
			String className = jarEntry.getName().substring(0, jarEntry.getName().length() - 6);
			className = className.replace('/', '.');
			Class c = cl.loadClass(className);
			classes.add(c);
		}

		return classes;
	}

	public static void copyClassToDir(String jarFile) throws  IOException {
		JarFile jar = new JarFile(jarFile);
		java.util.Enumeration enumEntries = jar.entries();
		while (enumEntries.hasMoreElements()) {
			java.util.jar.JarEntry file = (java.util.jar.JarEntry) enumEntries.nextElement();

			//Nedd set temp folder
			java.io.File f = new java.io.File(DEFAULT_TMP_DIR_PATH + "/" + file.getName());
			if (file.isDirectory()) {
				// if its a directory, create it
				f.mkdir();
				continue;
			}

			java.io.InputStream is = jar.getInputStream(file);
			// get the input stream
			java.io.FileOutputStream fos = new java.io.FileOutputStream(f);
			while (is.available() > 0) {
				// write contents of 'is' to 'fos'
				fos.write(is.read());
			}

			fos.close();
			is.close();
		}

	}
}
