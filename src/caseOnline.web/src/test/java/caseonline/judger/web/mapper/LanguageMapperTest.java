package caseonline.judger.web.mapper;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import caseonline.judger.web.model.Language;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:test-spring-context.xml" })
public class LanguageMapperTest {
	
	/**
	 * 测试创建语言方法createLanguage()
	 */
	@Ignore
	@Test
	public void testCreateLanguage(){
		Language language=new Language();
		int result=languageMapper.createLanguage(language);
		assertEquals(1, result);
	}
	/**
	 * 测试更新语言updatelanguage()
	 */
	@Test
	public void testUpdateLanguage(){
		Language languages=new Language();
		int result=languageMapper.updateLanguage(languages);
		assertEquals(1, result);
	}
	/**
	 * 测试根据语言ID查询语言getLanguageById();
	 */
	@Ignore
	@Test
	public void testGetLanguageById(){
		Language language=languageMapper.getLanguageById(1);
		assertNotNull(language);
		String languageName=language.getLanguageName();
		assertEquals("java", languageName);
	}
	/**
	 * 测试根据语言名字查询语言getLanguageByName();
	 */
	@Ignore
	@Test
	public void testGetlanguageByName(){
		Language language=languageMapper.getLanguageByName("java");
		assertNotNull(language);
		String languageName=language.getLanguageName();
		assertEquals("java", languageName);
	}
	/**
	 * 测试获取所有语言getAllLanguages()
	 */
	@Ignore
	@Test
	public void testGetAllLanguages(){
		List<Language> languages=languageMapper.getAllLanguages();
		int result=languages.size();
		assertEquals(1, result);
	}
	
	@Autowired
	LanguageMapper languageMapper;
	
	
}
