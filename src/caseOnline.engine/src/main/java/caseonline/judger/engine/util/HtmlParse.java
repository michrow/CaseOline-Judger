package caseonline.judger.engine.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 解析HTML，获取覆盖率
 */
public class HtmlParse {

	/** The encode. */
	private static String ENCODE = "GBK";

	/**
	 * 获取HTML文件
	 *
	 * @param szFileName
	 *            the sz file name
	 * @return the string
	 */
	private static String openFile(String szFileName) {
		try {
			BufferedReader bis = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(szFileName)), ENCODE));
			String szContent = "";
			String szTemp;
			while ((szTemp = bis.readLine()) != null) {
				szContent += szTemp + "\n";
			}
			bis.close();
			return szContent;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 解析HTML
	 *
	 * @param szFileName
	 *            the sz file name
	 * @param languageName 
	 * @param testName  --待测试类名（该参数主要针对java覆盖率的解析）
	 * @return --覆盖率信息Map
	 */
	public static Map<String, List<String>> parseHtml(String szFileName, String languageName) {
		Map<String, List<String>> coverage = new HashMap<String, List<String>>();
		String html = openFile(szFileName);
		if (!html.isEmpty()) {
			Document document = Jsoup.parse(html);
			String subThead="";
			if (languageName.equalsIgnoreCase("java")) {
				subThead="td";
			}else if (languageName.equalsIgnoreCase("python")) {
				subThead="th";
			}
			Elements heads = document.getElementsByTag("thead").select(subThead);
			Elements foots = document.getElementsByTag("tfoot").select("td");
			Elements bodys = document.getElementsByTag("tbody").select("tr");
			List<String> head = new ArrayList<String>();
			for (int i = 1; i < heads.size(); i++) {
				head.add(heads.get(i).text());
			}
			coverage.put(heads.get(0).text(), head);
			for (Iterator<Element> iterator = bodys.iterator(); iterator.hasNext();) {
				Element item = iterator.next();
				Elements is = item.select("td");

				List<String> body = new ArrayList<String>();
				for (int i = 1; i < is.size(); i++) {
					Element it = is.get(i);
					String itemValue = it.text();
					if (itemValue.isEmpty()) {
						itemValue = "TAG";
					}
					body.add(itemValue);
				}
				coverage.put(is.get(0).text(), body);
			}
			List<String> foot = new ArrayList<String>();
			for (int i = 1; i < foots.size(); i++) {
				Element item = foots.get(i);
				String itemValue = item.text();
				foot.add(itemValue);
			}
			coverage.put(foots.get(0).text(), foot);
		}
		return coverage;
	}
}
