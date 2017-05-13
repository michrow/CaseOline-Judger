package caseonline.judger.engine.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import caseonline.judger.engine.model.Option;

/**
 * The Interface OptionMapper.
 */
public interface OptionMapper {

	/**
	 * Gets the option by key.
	 *
	 * @param key
	 *            the key
	 * @return the option by key
	 */
	public Option getOptionByKey(@Param("key") String key);

	/**
	 * Update option by key.
	 *
	 * @param option
	 *            the option
	 * @return the int
	 */
	public int updateOptionByKey(Option option);

	/**
	 * Gets the all option.
	 *
	 * @return the all option
	 */
	public List<Option> getAllOption();

}
