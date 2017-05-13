package caseonline.judger.engine.util;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 文件处理
 */
public class FileManager {

	/**
	 * Instantiates a new file manager.
	 */
	private FileManager() {
	};

	/**
	 * 移动文件
	 *
	 * @param srcFile
	 *            --源文件
	 * @param destPath
	 *            --目标文件
	 * @return true, if successful
	 */
	public static boolean move(String srcFile, String destPath) {
		File file = new File(srcFile);
		File dir = new File(destPath);
		boolean success = file.renameTo(new File(dir, file.getName()));
		return success;
	}

	/**
	 * 创建文件
	 *
	 * @param path
	 *            the path
	 * @return the file
	 */
	public static File getFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			LOGGER.warn(String.format("创建单个文件%s失败，目标文件已存在！", new Object[] { path }));
		}
		if (path.endsWith(File.separator)) {
			LOGGER.warn(String.format("创建单个文件%s失败，目标文件不能为目录！", new Object[] { path }));
		}
		// 判断目标文件所在的目录是否存在
		if (!file.getParentFile().exists()) {
			// 如果目标文件所在的目录不存在，则创建父目录
			LOGGER.warn("目标文件所在目录不存在，准备创建它！");
			if (!file.getParentFile().mkdirs()) {
				LOGGER.warn("创建目标文件所在目录失败！");
			}
		}
		// 创建目标文件
		try {
			if (file.createNewFile()) {
				LOGGER.warn(String.format("创建单个文件%s成功！", new Object[] { path }));
			} else {
				LOGGER.warn(String.format("创建单个文件%s失败！", new Object[] { path }));
			}
		} catch (IOException e) {
			LOGGER.warn(String.format("创建单个文件%s失败！%s", new Object[] { path, e.getMessage() }));
		}
		return file;
	}

	/**
	 * 删除文件
	 *
	 * @param workspaceRootPath
	 *            the workspace root path
	 */
	// 删除文件和目录
	public static void clearFiles(String workspaceRootPath) {
		File file = new File(workspaceRootPath);
		if (file.exists()) {
			deleteFile(file);
		}
	}

	/**
	 * Delete file.
	 *
	 * @param file
	 *            the file
	 */
	private static void deleteFile(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteFile(files[i]);
			}
		}
		file.delete();
	}

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager.getLogger(FileManager.class);
}
