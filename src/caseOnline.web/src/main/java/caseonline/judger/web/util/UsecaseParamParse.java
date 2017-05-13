package caseonline.judger.web.util;

import java.util.ArrayList;
import java.util.List;

public class UsecaseParamParse {
	private UsecaseParamParse() {
	}

	public static List<List<Object>> Parse(String usecase) {

		List<List<Object>> lists = new ArrayList<List<Object>>();
		String[] usecases = usecase.split("\n");
		for (String ucase : usecases) {
			String[] params = ucase.substring(1, ucase.length() - 1).split(",");
			List<Object> list = new ArrayList<Object>();
			for (String param : params) {
				list.add(param);
			}
			lists.add(list);
		}
		return lists;
	}
}
