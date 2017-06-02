package caseonline.judger.engine.mapper;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;

import caseonline.judger.engine.model.Language;

/**
 * The Interface LanguageMapper.
 */
@CacheNamespace(implementation = org.mybatis.caches.ehcache.EhcacheCache.class)
public interface LanguageMapper {

	/**
	 * 通过ID来删除语言.
	 *
	 * @param --语言ID
	 * @return --删除结果（0：失败，1成功）
	 */
	int deleteLanguage(@Param("languageId") Integer languageId);

	/**
	 * 创建语言.
	 *
	 * @param --语言实体
	 * @return --创建结果
	 */
	int createLanguage(Language language);

	/**
	 * 更新语言.
	 *
	 * @param --要更新的语言信息实体
	 * @return 更新结果
	 */
	int updateLanguage(Language language);

	/**
	 * 通过语言ID获取.
	 *
	 * @param --语言ID
	 * @return --获取的语言对象
	 */
	Language getLanguageById(@Param("languageId") Integer languageId);

	/**
	 * 更具语言的名字获取语言对象.
	 *
	 * @param --语言名字
	 * @return --获取的结果对象
	 */
	Language getLanguageByName(@Param("languageName") String languageName);

	/**
	 * 获取所有语言.
	 *
	 * @return --所有语言集合
	 */
	List<Language> getAllLanguages();

}