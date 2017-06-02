package caseonline.judger.engine.util;

import java.util.List;

/**
 * 用于生成测试用例的 Bean.
 */
public class Bean {

	/** 各种包. */
	private List<String> heads;

	/** The author. */
	private String author;

	/** The class name. */
	private String className;

	/** The create time. */
	private String createTime;

	/** 参数. */
	List<Param> paramInfo;
	
	/** 待测试类名. */
	private String testClassName;

	/**
	 * Gets the 各种包.
	 *
	 * @return the 各种包
	 */
	public List<String> getHeads() {
		return heads;
	}

	/**
	 * Sets the 各种包.
	 *
	 * @param heads
	 *            the new 各种包
	 */
	public void setHeads(List<String> heads) {
		this.heads = heads;
	}

	/**
	 * Gets the author.
	 *
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Sets the author.
	 *
	 * @param author
	 *            the new author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * Gets the class name.
	 *
	 * @return the class name
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Sets the class name.
	 *
	 * @param className
	 *            the new class name
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * Gets the create time.
	 *
	 * @return the create time
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * Sets the create time.
	 *
	 * @param createTime
	 *            the new create time
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * Gets the 参数.
	 *
	 * @return the 参数
	 */
	public List<Param> getParamInfo() {
		return paramInfo;
	}

	/**
	 * Sets the 参数.
	 *
	 * @param paramInfo
	 *            the new 参数
	 */
	public void setParamInfo(List<Param> paramInfo) {
		this.paramInfo = paramInfo;
	}

	/**
	 * @return the testClassName
	 */
	public String getTestClassName() {
		return testClassName;
	}

	/**
	 * @param testClassName the testClassName to set
	 */
	public void setTestClassName(String testClassName) {
		this.testClassName = testClassName;
	}

}
