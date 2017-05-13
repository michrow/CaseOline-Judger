package caseonline.judger.web.util;

import java.util.HashMap;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class ProductUsecase.
 */
public class ProductUsecase {
	
	/**
	 * Instantiates a new product usecase.
	 */
	private ProductUsecase(){}
	
	/**
	 * 生成测试用例代码.
	 *
	 * @param --数据形式的用例
	 * @return --是否生成成功
	 */
	public static Map<String, Object> createUsecase(String originUsecase) {
		Map<String, Object> result=new HashMap<>();
		result.put("usecase", originUsecase);
		result.put("size", 5);
		result.put("isSuccessful", true);
		return result;
	}
	/**
	 * 获取参数信息
	 * @param code
	 * @return
	 */
	public static String parseParamInfo(String code){
		return null;
	}
}
