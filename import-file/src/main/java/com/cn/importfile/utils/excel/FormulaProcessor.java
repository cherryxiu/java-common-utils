package com.cn.importfile.utils.excel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormulaProcessor {
	private static FormulaProcessor self = null;

	private Pattern pattern = Pattern.compile("\\$(\\d+)");

	private FormulaProcessor() {

	}

	public static FormulaProcessor getInstance() {
		if (self == null)
			self = new FormulaProcessor();
		return self;
	}

	public void fillValue(TableDataRow row) {

	}

	@SuppressWarnings("unused")
	private String convertFormula(String formula) {
		Matcher m = pattern.matcher(formula);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "getValue(row, " + m.group(1) + ")");
		}
		m.appendTail(sb);

		return sb.toString();
	}
}
