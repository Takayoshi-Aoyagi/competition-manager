package com.tkdksc.excel.writer;

import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
		writeClassfiedTable();
	}

	@Override
	protected void writeClassfiedTable() {
		// ヘッダ
		writeTableHeaderRow();
		nextLine();
		//
		for (String categoryName : categoryMap.keySet()) {
			Category category = categoryMap.get(categoryName);
			if (!"マッソギ".equals(categoryName) && !"トゥル".equals(categoryName)
					&& !"スペシャル".equals(categoryName)) {
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

	private void writeTableBodyRow(Object[] rowData) {
		XSSFRow row = sheet.createRow(rowNum);
		int index = 0;
		for (Object data : rowData) {
			Cell cell = writeCell(data, row, index);
			cell.setCellStyle(normalStyle);
			index++;
		}
	}

	@Override
	protected String[] getHeaderTitles() {
		String[] headers = { "種目", "クラス", "優勝", "準優勝", "第三位", "第三位" };
		return headers;
	}
}
