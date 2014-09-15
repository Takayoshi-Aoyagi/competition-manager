package com.tkdksc.excel.writer.aggregate;

import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tkdksc.AggregationGroup;
import com.tkdksc.core.Category;

public class ResultSheetWriter extends AbstractExcelSheetWriter {

	private Map<String, Category> categoryMap;

	public ResultSheetWriter(XSSFWorkbook wb,
			Map<String, Category> categoryMap, XSSFCellStyle normalStyle,
			XSSFCellStyle titleStyle) {
		super(wb, "リザルト", normalStyle, titleStyle);
		this.categoryMap = categoryMap;
	}

	@Override
	protected void writeBody() {
		writeClassfiedTable(null);
	}

	@Override
	protected void writeClassfiedTable(String classification2) {
		// header
		writeTableHeaderRow();
		nextLine();
		// body
		for (String categoryName : categoryMap.keySet()) {
			Category category = categoryMap.get(categoryName);
			if (!AggregationGroup.MASSOGI.getKana().equals(categoryName)
					&& !AggregationGroup.TUL.getKana().equals(categoryName)
					&& !AggregationGroup.SPECIAL.getKana().equals(categoryName)) {
				continue;
			}
			for (String classification : category.getMap().keySet()) {
				if ("×".equals(classification)) {
					continue;
				}
				String[] rowData = { categoryName, classification, "", "", "",
						"" };
				writeTableBodyRow(rowData);
				nextLine();
			}
			String[] rowData = { "団体トゥル", "", "", "", "", "" };
			writeTableBodyRow(rowData);
		}
	}

	@Override
	protected String[] getHeaderTitles() {
		String[] headers = { "種目", "クラス", "優勝", "準優勝", "第三位", "第三位" };
		return headers;
	}
}
