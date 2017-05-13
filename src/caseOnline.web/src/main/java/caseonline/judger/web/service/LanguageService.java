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

@Service
public class LanguageService {
	
	public List<Language> getAllLanguages(){
		return languageMapper.getAllLanguages();
	}
	
	

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
	
	public List<UsecaseFormat> getAllFormat(){
		return formatMapper.getAllFormats();
	}
	
	private boolean addLanguge(List<Language> addLanguges) {
		
		for (Language language : addLanguges) {
			languageMapper.createLanguage(language);
		}
		return true;
	}



	private boolean updateLanguage(List<Language> updateLanguages) {
		for (Language language : updateLanguages) {
			languageMapper.updateLanguage(language);
		}
		return true;
	}



	private boolean deleteLanguage(List<Integer> deleteLanguages) {
		for (Integer languageId : deleteLanguages) {
			languageMapper.deleteLanguage(languageId);
		}
		return true;
	}

	@Autowired
	LanguageMapper languageMapper;
	
	@Autowired
	UsecaseFormatMapper formatMapper;
}
