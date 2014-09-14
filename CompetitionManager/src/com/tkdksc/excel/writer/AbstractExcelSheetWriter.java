package com.tkdksc.excel.writer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public abstract class AbstractExcelSheetWriter {

	protected XSSFWorkbook wb;
	protected String title;
	protected XSSFCellStyle normalStyle;
	protected XSSFCellStyle titleStyle;
	protected XSSFSheet sheet;
	protected int rowNum = 0;

	protected AbstractExcelSheetWriter(XSSFWorkbook wb, String title,
			XSSFCellStyle normalStyle, XSSFCellStyle titleStyle) {
		this.wb = wb;
		this.title = title;
		this.normalStyle = normalStyle;
		this.titleStyle = titleStyle;
		sheet = wb.createSheet(title);
	}

	protected XSSFCell writeStringCell(String str, XSSFRow row, int index) {
		XSSFCell cell = row.createCell(index);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(str);
		return cell;
	}

	protected XSSFCell writeNumericCell(int num, XSSFRow row, int index) {
		XSSFCell cell = row.createCell(index);
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(num);
		return cell;
	}

	protected void write() {
		writeTitle();
		writeBody();
	}

	protected void writeTitle() {
		writeTitleRow(title, rowNum, sheet);
		rowNum += 2;
	}

	protected abstract void writeBody();

	private void writeTitleRow(String title, int rowNum, XSSFSheet sheet) {
		XSSFRow titleRow = sheet.createRow(rowNum);
		XSSFCell titleCell = titleRow.createCell(0);
		titleCell.setCellType(Cell.CELL_TYPE_STRING);
		titleCell.setCellValue(title);
	}
}