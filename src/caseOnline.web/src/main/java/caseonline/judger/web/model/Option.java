package caseonline.judger.web.model;

/**
 * 系统属性
 */
public class Option {

	/**
	 * Instantiates a new option.
	 */
	public Option() {
	}

	/**
	 * Instantiates a new option.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @param id
	 *            the id
	 */
	public Option(String key, String value, int id) {
		this(key, value);
		this.id = id;
	}

	/**
	 * Instantiates a new option.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public Option(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Sets the key.
	 *
	 * @param key
	 *            the new key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/** The key. */
	private String key;

	/** The value. */
	private String value;

	/** The id. */
	private int id;
}
