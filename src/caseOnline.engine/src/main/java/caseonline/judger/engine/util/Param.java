package caseonline.judger.engine.util;

import java.util.List;

/**
 * 用于生成测试用例 Param.
 */
public class Param {



	/** 方法名. */
	private String methodName;

	/** 希望的值. */
	private Object expectValue;

	/** 方法参数. */
	List<Object> params;

	/**
	 * Gets the 方法名.
	 *
	 * @return the 方法名
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * Sets the 方法名.
	 *
	 * @param methodName
	 *            the new 方法名
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * Gets the 希望的值.
	 *
	 * @return the 希望的值
	 */
	public Object getExpectValue() {
		return expectValue;
	}

	/**
	 * Sets the 希望的值.
	 *
	 * @param expectValue
	 *            the new 希望的值
	 */
	public void setExpectValue(Object expectValue) {
		this.expectValue = expectValue;
	}

	/**
	 * Gets the 方法参数.
	 *
	 * @return the 方法参数
	 */
	public List<Object> getParams() {
		return params;
	}

	/**
	 * Sets the 方法参数.
	 *
	 * @param params
	 *            the new 方法参数
	 */
	public void setParams(List<Object> params) {
		this.params = params;
	}

}
