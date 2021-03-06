package caseonline.judger.engine.mapper;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;

import caseonline.judger.engine.model.UsecaseFormat;

/**
 * The Interface UsecaseFormatMapper.
 */
@CacheNamespace(implementation = org.mybatis.caches.ehcache.EhcacheCache.class)
public interface UsecaseFormatMapper {

	/**
	 * Gets the usecase format.
	 *
	 * @param formatId
	 *            the format id
	 * @return the usecase format
	 */
	UsecaseFormat getUsecaseFormat(@Param("formatId") Integer formatId);

	/**
	 * Delete usercase format.
	 *
	 * @param formatId
	 *            the format id
	 * @return the int
	 */
	int deleteUsecaseFormat(@Param("formatId") Integer formatId);

	/**
	 * Creates the usercase format.
	 *
	 * @param format
	 *            the format
	 * @return the int
	 */
	int createUsecaseFormat(UsecaseFormat format);

	/**
	 * Update usercase format.
	 *
	 * @param format
	 *            the format
	 * @return the int
	 */
	int updateUsecaseFormat(UsecaseFormat format);

	List<UsecaseFormat> getAllFormats();

}