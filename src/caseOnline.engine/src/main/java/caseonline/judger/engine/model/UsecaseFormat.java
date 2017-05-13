package caseonline.judger.engine.model;

import java.io.Serializable;

/**
 * 用例格式
 */
public class UsecaseFormat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3697916040112885218L;

	/**
	 * Instantiates a new usecase formats.
	 */
	public UsecaseFormat() {
	}

	/**
	 * Instantiates a new usecase formats.
	 *
	 * @param formatText
	 *            the format text
	 */
	public UsecaseFormat(String formatText) {
		super();
		this.formatText = formatText;
	}

	/**
	 * Instantiates a new usecase formats.
	 *
	 * @param formatId
	 *            the format id
	 * @param formatText
	 *            the format text
	 */
	public UsecaseFormat(int formatId, String formatText) {
		this.formatId = formatId;
		this.formatText = formatText;
	}

	/** 格式ID. */
	private Integer formatId;

	/** 格式文本. */
	private String formatText;

	/** 备用字段 1. */
	private String formatbak1;

	/** 备用字段 2. */
	private String formatbak2;

	/** 备用字段 3. */
	private String formatbak3;

	/**
	 * Gets the 格式ID.
	 *
	 * @return the 格式ID
	 */
	public Integer getFormatId() {
		return formatId;
	}

	/**
	 * Sets the 格式ID.
	 *
	 * @param formatId
	 *            the new 格式ID
	 */
	public void setFormatId(Integer formatId) {
		this.formatId = formatId;
	}

	/**
	 * Gets the 备用字段 1.
	 *
	 * @return the 备用字段 1
	 */
	public String getFormatbak1() {
		return formatbak1;
	}

	/**
	 * Sets the 备用字段 1.
	 *
	 * @param formatbak1
	 *            the new 备用字段 1
	 */
	public void setFormatbak1(String formatbak1) {
		this.formatbak1 = formatbak1;
	}

	/**
	 * Gets the 备用字段 2.
	 *
	 * @return the 备用字段 2
	 */
	public String getFormatbak2() {
		return formatbak2;
	}

	/**
	 * Sets the 备用字段 2.
	 *
	 * @param formatbak2
	 *            the new 备用字段 2
	 */
	public void setFormatbak2(String formatbak2) {
		this.formatbak2 = formatbak2;
	}

	/**
	 * Gets the 备用字段 3.
	 *
	 * @return the 备用字段 3
	 */
	public String getFormatbak3() {
		return formatbak3;
	}

	/**
	 * Sets the 备用字段 3.
	 *
	 * @param formatbak3
	 *            the new 备用字段 3
	 */
	public void setFormatbak3(String formatbak3) {
		this.formatbak3 = formatbak3;
	}

	/**
	 * Gets the 格式文本.
	 *
	 * @return the 格式文本
	 */
	public String getFormatText() {
		return formatText;
	}

	/**
	 * Sets the 格式文本.
	 *
	 * @param formatText
	 *            the new 格式文本
	 */
	public void setFormatText(String formatText) {
		this.formatText = formatText;
	}

}