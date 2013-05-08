package com.mdmp.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.util.LineReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HadoopUtils {

	private static final Logger log = LoggerFactory
			.getLogger(HadoopUtils.class);

	private static FileSystem fs;
	private static CompressionCodecFactory codecFactory;

	public static void setFileSystem(FileSystem fileSystem) {
		fs = fileSystem;
	}

	static {
		try {
			init();
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public static void init() throws Exception {
		Configuration conf = new Configuration();
		conf.addResource("hdfs-site.xml");
		conf.addResource("mapred-site.xml");
		conf.addResource("core-site.xml");

		init(conf);
	}

	private static void init(Configuration conf) throws Exception {
		try {
			fs = FileSystem.get(conf);

			if (!(fs instanceof DistributedFileSystem)) {
				throw new IOException("HDFS required. current:" + fs.getUri());
			}
		} catch (Exception e) {
			log.error("fail to connect to hadoop", e);
			throw e;
		}
	}

	public static FileSystem getFileSystem() {
		return fs;
	}

	public static boolean existPath(Path path) throws IOException {
		return getFileSystem().exists(path);
	}

	public static boolean existPath(String path) throws IOException {
		return existPath(new Path(path));
	}

	public static long getLastModifiedTime(String path) throws IOException {
		return getFileSystem().getFileStatus(new Path(path))
				.getModificationTime();
	}

	public static InputStream openFile(String path) throws IOException {
		return openFile(new Path(path));
	}

	public static InputStream openFile(Path path) throws IOException {
		FileSystem fs = getFileSystem();
		if (!fs.exists(path)) {
			return null;
		}
		return fs.open(path);
	}

	public static List<String> getSubDirs(Path parentDir) throws IOException {
		if (!existPath(parentDir)) {
			return null;
		}
		FileSystem fs = getFileSystem();
		List<String> subPaths = new ArrayList<String>();
		FileStatus fss = fs.getFileStatus(parentDir);
		if (fss.isDirectory()) {
			FileStatus[] fsArray = fs.listStatus(parentDir);
			for (FileStatus status : fsArray) {
				subPaths.add(status.getPath().getName());
			}
			return subPaths;
		}
		return null;
	}

	public static List<String> getSubDirs(String parentDir) throws IOException {
		return getSubDirs(new Path(parentDir));
	}

	/**
	 * @param parentDir
	 * @return all files and folder under the parentDir
	 * @throws IOException
	 */
	public static FileStatus[] listAll(Path parentDir) throws IOException {
		if (parentDir == null) {
			return null;
		}
		if (!existPath(parentDir)) {
			return null;
		}
		FileSystem fs = getFileSystem();
		FileStatus fss = fs.getFileStatus(parentDir);
		if (fss.isDirectory()) {
			return fs.listStatus(parentDir);
		}
		return null;
	}

	/**
	 * @param path
	 * @return the codec which can be used for the specified path
	 */
	public static CompressionCodec getCodec(Path path) {
		return codecFactory.getCodec(path);
	}

	/**
	 * Get the lineReader which can be used to read uncompressed lines from the
	 * path. Current, Hive only support '\n' as the lines terminator.
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static LineReader getLineReader(Path path) throws IOException {
		InputStream inputStream = openFile(path);
		CompressionCodec codec = getCodec(path);
		return new LineReader(codec == null ? inputStream
				: codec.createInputStream(inputStream));
	}

	public static long getFileTotalSize(String path) throws IOException {
		Path hdfsPath = new Path(path);
		FileSystem fs = getFileSystem();
		FileStatus stauts = fs.getFileStatus(hdfsPath);
		return getFileTotalSize(fs, stauts);
	}

	public static long getFileTotalSize(FileSystem fs, FileStatus stauts)
			throws IOException {
		long totalSize = 0;
		if (!stauts.isDirectory()) {
			// FileStatus stauts = fs.getFileStatus(hdfsPath);
			totalSize = stauts.getLen();
		} else {
			FileStatus[] stautsArray = fs.listStatus(stauts.getPath());
			for (FileStatus subStauts : stautsArray) {
				totalSize += getFileTotalSize(fs, subStauts);
			}
		}
		return totalSize;
	}

	public static void writer(String uri, String value) throws IOException {
		Path path = new Path(uri);

		FSDataOutputStream out = null;
		FileSystem fs = getFileSystem();
		if (fs.exists(path)) {
			out = fs.append(path);
		} else {
			out = fs.create(path);
		}

		out.writeBytes(value);
		out.close();
	}

	public static FSDataOutputStream writeWithoutClose(String uri, String value)
			throws IOException {
		Path path = new Path(uri);

		FSDataOutputStream out = null;
		FileSystem fs = getFileSystem();
		if (fs.exists(path)) {
			out = fs.append(path);
		} else {
			out = fs.create(path);
		}

		out.writeBytes(value);
		out.flush();
		out.sync();
		return out;
	}

	public static boolean deleteFile(String filePath) throws IOException {
		Path path = new Path(filePath);
		return fs.delete(path, false);
	}

	public static boolean deleteFolder(String filePath) throws IOException {
		Path path = new Path(filePath);
		return fs.delete(path, true);
	}

	public static boolean move(String from, String to) throws IOException {
		return fs.rename(new Path(from), new Path(to));
	}
	
	public static void copyFromLocalFile(String f1, String f2) throws IOException{
		Path file1 = new Path(f1);
		Path file2 = new Path(f2);
		fs.copyFromLocalFile(file1, file2);
	}
}
