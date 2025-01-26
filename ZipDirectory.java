package com.ggl.zipdirectory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipDirectory {

	public static void main(String[] args) {
		if (args.length >= 1) {
			try {
				new ZipDirectory(args).zipDirectory(args[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("The directory path must be passed "
					+ "as the first and only parameter.");
		}
	}
	
	private final List<String> exclusionList;
	
	public ZipDirectory(String[] args) {
		this.exclusionList =  new ArrayList<>();
		this.exclusionList.add(".zip");
		
		for (int index = 1; index < args.length; index++) {
			this.exclusionList.add("." + args[index]);
		}
	}

	public void zipDirectory(String path) throws IOException {
		
		File folder = new File(path);
		List<File> fileList = createFileList(folder);
		
		String separator = System.getProperty("file.separator");
		String directory = getDirectoryName(path, separator);
		
		String zipFile = path + separator + directory + ".zip";
		System.out.println("Creating zip file: " + zipFile);
		
		File file = new File(zipFile);
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file));
		
		for (File f : fileList) {
			String fileName = f.getPath();
//			System.out.println(fileName);
			if (isFileIncluded(fileName)) {
				String partName = fileName.substring(path.length() + 1);
				partName = partName.replaceAll("\\\\", "/");
				System.out.println("Writing " + partName + " to zip file");
				if (f.isDirectory()) {
//					System.out.println(partName + "/");
					ZipEntry ze = new ZipEntry(partName + "/");
					out.putNextEntry(ze);
					out.closeEntry();
				} else {
//					System.out.println(partName);
					ZipEntry ze = new ZipEntry(partName);
					out.putNextEntry(ze);
					byte[] fileContent = Files.readAllBytes(f.toPath());
					out.write(fileContent, 0, fileContent.length);
					out.closeEntry();
				}
			}
		}
		
		out.finish();
		out.close();
	}

	private List<File> createFileList(File directory) {
		List<File> fileList = new ArrayList<>();
		listFilesForFolder(directory, fileList);

//		for (File f : fileList) {
//			System.out.println(f.getPath());
//		}

		return fileList;
	}

	private void listFilesForFolder(File directory, List<File> fileList) {
		for (final File fileEntry : directory.listFiles()) {
			if (fileEntry.isDirectory()) {
				fileList.add(fileEntry);
				listFilesForFolder(fileEntry, fileList);
			} else {
				fileList.add(fileEntry);
			}
		}
	}
	
	private String getDirectoryName(String path, String separator) {
		String directory = "";
		int beginIndex = path.lastIndexOf(separator);
		if (beginIndex >= 0) {
			directory = path.substring(beginIndex + 1);
		} else {
			directory = path;
		}
		
		return directory;
	}
	
	private boolean isFileIncluded(String fileName) {
		for (String extension : exclusionList) {
			if (fileName.endsWith(extension)) {
				return false;
			}
		}
		
		return true;
	}

}
