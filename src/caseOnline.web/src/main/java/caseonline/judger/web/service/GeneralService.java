package caseonline.judger.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import caseonline.judger.web.mapper.OptionMapper;
import caseonline.judger.web.model.Option;
@Service
public class GeneralService {
	
	public Map<String, String> GetAllOption(){
		List<Option> options=optionMapper.getAllOption();
		Map<String, String> result= new HashMap<>();
		for (Option option : options) {
			result.put(option.getKey(), option.getValue());
		}
		return result;
	}
	
	public int UpdateOption(String version,String allowUserRegister){
		
		Option optionV=optionMapper.getOptionByKey("version");
		optionV.setValue(version);
		Option optionA=optionMapper.getOptionByKey("allowUserRegister");
		optionA.setValue(allowUserRegister);
		
		optionMapper.updateOptionByKey(optionA);
		optionMapper.updateOptionByKey(optionV);
		return 1;
	}
	@Autowired
	OptionMapper optionMapper;
}
