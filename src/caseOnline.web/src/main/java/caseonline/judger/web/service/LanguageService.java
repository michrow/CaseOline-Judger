package caseonline.judger.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import caseonline.judger.web.mapper.LanguageMapper;
import caseonline.judger.web.mapper.UsecaseFormatMapper;
import caseonline.judger.web.model.Language;
import caseonline.judger.web.model.UsecaseFormat;

/**
 * 语言设置
 */
@Service
public class LanguageService {	
	
	/**
	 * 获取所有支持的原因.
	 *
	 * @return the all languages
	 */
	public List<Language> getAllLanguages(){
		return languageMapper.getAllLanguages();
	}
	
	/**
	 * 跟新语言设置.
	 *
	 * @param newLanguages the new languages
	 * @return the map
	 */
	public Map<String, Object> updateLanguageSettings(List<Language> newLanguages) {
		List<Language> addLanguges=new ArrayList<>();
		List<Integer> deleteLanguages=new ArrayList<>();
		List<Language> updateLanguages=new ArrayList<>();
		List<Language> oldLanguages=languageMapper.getAllLanguages();
		List<Integer> oldLanguageIds=new ArrayList<>();
		List<Integer> newLanguageIds=new ArrayList<>();
		
		//获取新增的语言
		for (Language language : newLanguages) {
			if (language.getLanguageId()==0) {
				addLanguges.add(language);
			}
		}
		
		//将新增的从变更中移除
		newLanguages.removeAll(addLanguges);
		
		for (Language language : newLanguages) {
			newLanguageIds.add(language.getLanguageId());
		}
		
		for (Language language : oldLanguages) {
			oldLanguageIds.add(language.getLanguageId());
		}	
		
		//获取删除的语言ID
		oldLanguageIds.removeAll(newLanguageIds);
		deleteLanguages.addAll(oldLanguageIds);
		
		//获取跟新的语言
		newLanguages.retainAll(oldLanguages); 
		updateLanguages.addAll(newLanguages);
		
		boolean resultD=deleteLanguage(deleteLanguages);
		boolean resultU=updateLanguage(updateLanguages);
		boolean resultA=addLanguge(addLanguges);
		
		Map<String, Object> result=new HashMap<>();
		if (resultA && resultD &&resultU) {
			result.put("isSuccessful", true);
		}else{
			result.put("isSuccessful", false);
		}
		return result;
	}
	
	/**
	 * 获取用例格式.
	 *
	 * @return the all format
	 */
	public List<UsecaseFormat> getAllFormat(){
		return formatMapper.getAllFormats();
	}
	
	/**
	 * 添加语言.
	 *
	 * @param addLanguges the add languges
	 * @return true, if successful
	 */
	private boolean addLanguge(List<Language> addLanguges) {
		
		for (Language language : addLanguges) {
			languageMapper.createLanguage(language);
		}
		return true;
	}

	/**
	 * 跟新语言.
	 *
	 * @param updateLanguages the update languages
	 * @return true, if successful
	 */
	private boolean updateLanguage(List<Language> updateLanguages) {
		for (Language language : updateLanguages) {
			languageMapper.updateLanguage(language);
		}
		return true;
	}

	/**
	 * 删除语言.
	 *
	 * @param deleteLanguages the delete languages
	 * @return true, if successful
	 */
	private boolean deleteLanguage(List<Integer> deleteLanguages) {
		for (Integer languageId : deleteLanguages) {
			languageMapper.deleteLanguage(languageId);
		}
		return true;
	}

	/** The language mapper. */
	@Autowired
	LanguageMapper languageMapper;
	
	/** The format mapper. */
	@Autowired
	UsecaseFormatMapper formatMapper;
}
