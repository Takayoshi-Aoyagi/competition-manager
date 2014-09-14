package com.tkdksc.excel.writer;

import java.util.ArrayList;
import java.util.List;
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
				writeTableBodyRow(categoryName, classification);
				nextLine();
			}
			writeTableBodyRow("団体トゥル", "");
		}
	}

	private void writeTableBodyRow(String categoryName, String classification) {
		XSSFRow row = sheet.createRow(rowNum);
		List<Cell> cells = new ArrayList<Cell>();
		cells.add(writeStringCell(categoryName, row, 0));
		cells.add(writeStringCell(classification, row, 1));
		cells.add(writeStringCell("", row, 2));
		cells.add(writeStringCell("", row, 3));
		cells.add(writeStringCell("", row, 4));
		cells.add(writeStringCell("", row, 5));
		for (Cell cell : cells) {
			cell.setCellStyle(normalStyle);
		}
	}

	@Override
	protected String[] getHeaderTitles() {
		String[] headers = { "種目", "クラス", "優勝", "準優勝", "第三位", "第三位" };
		return headers;
	}
}
