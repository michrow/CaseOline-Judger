package caseonline.judger.engine.exception;

/**
 * 创建文件夹失败的IO异常. 当java.io.File.File.mkdirs()返回false时被抛出.
 * 
 */
public class CreateDirectoryException extends Exception {
	/**
	 * IOException的构造函数.
	 * 
	 * @param message
	 *             错误消息
	 */
	public CreateDirectoryException(String message) {
		super(message);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7430055519184434330L;
}
