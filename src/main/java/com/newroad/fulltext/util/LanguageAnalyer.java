package com.newroad.fulltext.util;

import java.util.regex.Pattern;

import com.newroad.fulltext.service.IndexAnalyzerDict;

/**
 * @info LanguageAnalyer
 * @author tangzj1
 * @date Nov 27, 2013
 * @version
 */
public class LanguageAnalyer {

	// public static void main(String[] args) {
	// String[] strArr = new String[] { "www.micmiu.com",
	// "!@#$%^&*()_+{}[]|\"'?/:;<>,.", "！￥……（）——：；“”‘'《》，。？、", "不要啊",
	// "やめて", "test韩佳人", "???" };
	// for (String str : strArr) {
	// System.out.println("===========> 测试字符串：" + str);
	// System.out.println("正则判断结果：" + isChineseByREG(str) + " -- ");
	// System.out.println("Unicode判断结果 ：" + isChinese(str));
	// System.out.println("详细判断列表：");
	// char[] ch = str.toCharArray();
	// for (int i = 0; i < ch.length; i++) {
	// char c = ch[i];
	// System.out.println(c + " --> " + (isChinese(c) ? "是" : "否"));
	// }
	// }
	// }

	// 根据Unicode编码完美的判断中文汉字和符号
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) {
			return true;
		} else if (ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS) {
			return true;
		} else if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) {
			return true;
		} else if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B) {
			return true;
		} else if (ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) {
			return true;
		} else if (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		} else if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	// 完整的判断中文汉字和符号
	public static boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	// 只能判断部分CJK字符（CJK统一汉字）
	public static boolean isChineseByREG(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\u4E00-\\u9FA5]+");
		return pattern.matcher(str.trim()).find();
	}

	public static IndexAnalyzerDict chooseAnalyzer(String keyword) {
		boolean isChinese = isChinese(keyword);
		if (isChinese) {
			return IndexAnalyzerDict.NOTECN;
		} else {
			return IndexAnalyzerDict.NOTE;
		}
	}
}
