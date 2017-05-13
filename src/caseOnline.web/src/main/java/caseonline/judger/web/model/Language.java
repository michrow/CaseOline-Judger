package caseonline.judger.web.model;

import java.io.Serializable;

/**
 * The Class Language.
 */
public class Language implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6405106175319852458L;

	/**
	 * Instantiates a new language.
	 */
	public Language() {
	}

	/**
	 * Instantiates a new language.
	 *
	 * @param languageName
	 *            the language name
	 * @param buildFile
	 *            the build file
	 * @param format
	 *            the format
	 */
	public Language(String languageName, String buildFile, UsecaseFormat format) {
		super();
		this.languageName = languageName;
		this.buildFile = buildFile;
		this.format = format;
	}

	/**
	 * Instantiates a new language.
	 *
	 * @param languageId
	 *            the language id
	 * @param languageName
	 *            the language name
	 * @param buildFile
	 *            the build file
	 * @param format
	 *            the format
	 */
	public Language(Integer languageId, String languageName, String buildFile, UsecaseFormat format) {
		this(languageName, buildFile, format);
		this.languageId = languageId;

	}

	/** 语言ID. */
	private Integer languageId;

	/** 语言名字. */
	private String languageName;

	/** 构建文件. */
	private String buildFile;

	/** 案例格式. */
	private UsecaseFormat format;

	/** 备用字段 1. */
	private String languagebak1;

	/** 备用字段 2. */
	private String languagebak2;

	/** 备用字段 3. */
	private String languagebak3;

	/**
	 * Gets the 语言ID.
	 *
	 * @return the 语言ID
	 */
	public Integer getLanguageId() {
		return languageId;
	}

	/**
	 * Sets the 语言ID.
	 *
	 * @param languageId
	 *            the new 语言ID
	 */
	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	/**
	 * Gets the 语言名字.
	 *
	 * @return the 语言名字
	 */
	public String getLanguageName() {
		return languageName;
	}

	/**
	 * Sets the 语言名字.
	 *
	 * @param languageName
	 *            the new 语言名字
	 */
	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	/**
	 * Gets the 构建文件.
	 *
	 * @return the 构建文件
	 */
	public String getBuildFile() {
		return buildFile;
	}

	/**
	 * Sets the 构建文件.
	 *
	 * @param buildFile
	 *            the new 构建文件
	 */
	public void setBuildFile(String buildFile) {
		this.buildFile = buildFile;
	}

	/**
	 * Gets the 案例格式.
	 *
	 * @return the 案例格式
	 */
	public UsecaseFormat getFormat() {
		return format;
	}

	/**
	 * Sets the 案例格式.
	 *
	 * @param format
	 *            the new 案例格式
	 */
	public void setFormat(UsecaseFormat format) {
		this.format = format;
	}

	/**
	 * Gets the 备用字段 1.
	 *
	 * @return the 备用字段 1
	 */
	public String getLanguagebak1() {
		return languagebak1;
	}

	/**
	 * Sets the 备用字段 1.
	 *
	 * @param languagebak1
	 *            the new 备用字段 1
	 */
	public void setLanguagebak1(String languagebak1) {
		this.languagebak1 = languagebak1;
	}

	/**
	 * Gets the 备用字段 2.
	 *
	 * @return the 备用字段 2
	 */
	public String getLanguagebak2() {
		return languagebak2;
	}

	/**
	 * Sets the 备用字段 2.
	 *
	 * @param languagebak2
	 *            the new 备用字段 2
	 */
	public void setLanguagebak2(String languagebak2) {
		this.languagebak2 = languagebak2;
	}

	/**
	 * Gets the 备用字段 3.
	 *
	 * @return the 备用字段 3
	 */
	public String getLanguagebak3() {
		return languagebak3;
	}

	/**
	 * Sets the 备用字段 3.
	 *
	 * @param languagebak3
	 *            the new 备用字段 3
	 */
	public void setLanguagebak3(String languagebak3) {
		this.languagebak3 = languagebak3;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Language) {
			Language language = (Language) obj;
			return languageId == language.getLanguageId();
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		Language language = this;
		return language.hashCode();
	}

}