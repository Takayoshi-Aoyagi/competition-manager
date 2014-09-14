package com.tkdksc.excel.writer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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
		// ヘッダ
		writeHeaderRow();
		rowNum++;
		//
		for (String categoryName : categoryMap.keySet()) {
			Category category = categoryMap.get(categoryName);
			if (!"マッソギ".equals(categoryName) && !"トゥル".equals(categoryName)
					&& !"スペシャル".equals(categoryName)) {
				continue;
			}
			for (String division : category.getMap().keySet()) {
				if ("×".equals(division)) {
					continue;
				}
				writeResultRow(rowNum, sheet, categoryName, division);
				rowNum++;
			}
			writeResultRow(rowNum, sheet, "団体トゥル", "");
		}
	}

	private void writeResultRow(int rowNum, XSSFSheet sheet,
			String categoryName, String division) {
		XSSFRow row = sheet.createRow(rowNum);
		List<Cell> cells = new ArrayList<Cell>();
		cells.add(writeStringCell(categoryName, row, 0));
		cells.add(writeStringCell(division, row, 1));
		cells.add(writeStringCell("", row, 2));
		cells.add(writeStringCell("", row, 3));
		cells.add(writeStringCell("", row, 4));
		cells.add(writeStringCell("", row, 5));
		for (Cell cell : cells) {
			cell.setCellStyle(normalStyle);
		}
	}

	private void writeHeaderRow() {
		XSSFRow row = sheet.createRow(rowNum);
		List<Cell> headerCells = new ArrayList<Cell>();
		headerCells.add(writeStringCell("種目", row, 0));
		headerCells.add(writeStringCell("クラス", row, 1));
		headerCells.add(writeStringCell("優勝", row, 2));
		headerCells.add(writeStringCell("準優勝", row, 3));
		headerCells.add(writeStringCell("第三位", row, 4));
		headerCells.add(writeStringCell("第三位", row, 5));
		for (Cell cell : headerCells) {
			cell.setCellStyle(titleStyle);
		}
	}
}
