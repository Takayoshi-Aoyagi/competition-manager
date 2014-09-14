package com.tkdksc.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tkdksc.core.Category;
import com.tkdksc.core.Player;

public class ExcelWriter {

	private XSSFCellStyle titleStyle;
	private XSSFCellStyle normalStyle;

	public void write2excel(Map<String, Category> categoryMap)
			throws IOException {
		FileOutputStream fos = new FileOutputStream(new File(
				"output/entry_list.xlsx"));
		try {
			XSSFWorkbook wb = new XSSFWorkbook();
			initStyles(wb);
			for (String key : categoryMap.keySet()) {
				Category category = categoryMap.get(key);
				writeBySheet(wb, key, category);
			}
			writeResultSheet(wb, categoryMap);
			wb.write(fos);
		} finally {
			fos.close();
		}
	}

	private void writeResultSheet(XSSFWorkbook wb,
			Map<String, Category> categoryMap) {
		int rowNum = 0;
		String title = "リザルト";
		XSSFSheet sheet = wb.createSheet(title);
		// タイトル
		writeTitleRow(title, rowNum, sheet);
		rowNum += 2;
		// ヘッダ
		XSSFRow headerRow = sheet.createRow(rowNum);
		List<Cell> headerCells = new ArrayList<Cell>();
		headerCells.add(writeStringCell("種目", headerRow, 0));
		headerCells.add(writeStringCell("クラス", headerRow, 1));
		headerCells.add(writeStringCell("優勝", headerRow, 2));
		headerCells.add(writeStringCell("準優勝", headerRow, 3));
		headerCells.add(writeStringCell("第三位", headerRow, 4));
		headerCells.add(writeStringCell("第三位", headerRow, 5));
		for (Cell cell : headerCells) {
			cell.setCellStyle(titleStyle);
		}
		rowNum++;
		//
		for (String categoryName : categoryMap.keySet()) {
			Category category = categoryMap.get(categoryName);
			if (!"マッソギ".equals(categoryName) && !"トゥル".equals(categoryName) && !"スペシャル".equals(categoryName)) {
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

	private void initStyles(XSSFWorkbook wb) {
		titleStyle = wb.createCellStyle();
		titleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		titleStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT
				.getIndex());
		box(titleStyle);
		//
		normalStyle = wb.createCellStyle();
		box(normalStyle);
	}

	private void box(XSSFCellStyle style) {
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
	}

	private void writeBySheet(XSSFWorkbook wb, String key, Category category) {
		int rowNum = 0;
		XSSFSheet sheet = wb.createSheet(key);
		// タイトル
		writeTitleRow(key, rowNum, sheet);
		rowNum = rowNum + 2;
		//
		Map<String, List<Player>> map = category.getMap();
		for (String division : map.keySet()) {
			if ("×".equals(division)) {
				continue;
			}
			// header
			writeHeaderRow(wb, sheet, rowNum);
			rowNum++;
			// body
			List<Player> players = map.get(division);
			int numPlayer = 0;
			for (Player player : players) {
				numPlayer++;
				writeByRow(rowNum, sheet, division, player, numPlayer);
				rowNum++;
			}
			rowNum++;
		}
	}

	private void writeTitleRow(String title, int rowNum, XSSFSheet sheet) {
		XSSFRow titleRow = sheet.createRow(rowNum);
		XSSFCell titleCell = titleRow.createCell(0);
		titleCell.setCellType(Cell.CELL_TYPE_STRING);
		titleCell.setCellValue(title);
	}

	private void writeHeaderRow(XSSFWorkbook wb, XSSFSheet sheet, int rowNum) {
		XSSFRow row = sheet.createRow(rowNum);
		List<Cell> cells = new ArrayList<Cell>();
		cells.add(writeStringCell("No.", row, 0));
		cells.add(writeStringCell("カテゴリー", row, 1));
		cells.add(writeStringCell("氏名", row, 2));
		cells.add(writeStringCell("かな", row, 3));
		cells.add(writeStringCell("級位・段位", row, 4));
		cells.add(writeStringCell("道場", row, 5));
		for (Cell cell : cells) {
			cell.setCellStyle(titleStyle);
		}
	}

	private void writeByRow(int rowNum, XSSFSheet sheet, String division,
			Player player, int numPlayer) {
		XSSFRow row = sheet.createRow(rowNum);
		List<Cell> cells = new ArrayList<Cell>();
		cells.add(writeNumericCell(numPlayer, row, 0));
		cells.add(writeStringCell(division, row, 1));
		cells.add(writeStringCell(player.getName(), row, 2));
		cells.add(writeStringCell(player.getKana(), row, 3));
		cells.add(writeStringCell(player.getGrade(), row, 4));
		cells.add(writeStringCell(player.getDojo(), row, 5));
		for (Cell cell : cells) {
			cell.setCellStyle(normalStyle);
		}
	}

	private XSSFCell writeNumericCell(int num, XSSFRow row, int index) {
		XSSFCell cell = row.createCell(index);
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(num);
		return cell;
	}

	private XSSFCell writeStringCell(String str, XSSFRow row, int index) {
		XSSFCell cell = row.createCell(index);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(str);
		return cell;
	}
}
