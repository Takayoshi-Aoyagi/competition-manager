package com.tkdksc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	public static void copyEdge(XSSFWorkbook wb, XSSFSheet sheet, int size) throws IOException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File("data/excel/template.xlsx"));
			XSSFWorkbook template = new XSSFWorkbook(fis);
			XSSFSheet tempSheet = template.getSheet(Integer.toString(size));
			for (int i = 0; i < 100; i++) {
				XSSFRow tempRow = tempSheet.getRow(i);
				if (tempRow == null) {
					continue;
				}
				XSSFRow row = sheet.createRow(i);
				for (int j = 0; j < 5; j++) {
					XSSFCell tempCell = tempRow.getCell(j);
					if (tempCell == null) {
						continue;
					}
					XSSFCell cell = row.createCell(j);
//					cell.setCellStyle(STYLE);
					copyCellAttrs(wb, tempCell, cell);
				}
			}
		} finally {
			fis.close();
		}
	}


	private static void copyCellAttrs(XSSFWorkbook wb, XSSFCell tempCell, XSSFCell cell) {
		XSSFCellStyle tempStyle = tempCell.getCellStyle();
		XSSFCellStyle style = wb.createCellStyle();
		style.setBorderTop(tempStyle.getBorderTopEnum());
		style.setBorderBottom(tempStyle.getBorderBottomEnum());
		style.setBorderLeft(tempStyle.getBorderLeftEnum());
		style.setBorderRight(tempStyle.getBorderRightEnum());
		cell.setCellStyle(style);
	}

	private static CellStyle STYLE;

	public static void main(String[] args) throws IOException {
		
		FileOutputStream fos = null;
		try {
			XSSFWorkbook wb = new XSSFWorkbook();
			STYLE = wb.createCellStyle();
			STYLE.setBorderTop(CellStyle.BORDER_NONE);
			STYLE.setBorderBottom(CellStyle.BORDER_NONE);
			STYLE.setBorderLeft(CellStyle.BORDER_NONE);
			STYLE.setBorderRight(CellStyle.BORDER_NONE);
			XSSFSheet sheet = wb.createSheet("hoge");
			for (int i = 0; i < 200; i++) {
				sheet.setDefaultColumnStyle(i, STYLE);				
			}
			ExcelUtils.copyEdge(wb, sheet, 4);
			fos = new FileOutputStream(new File("hoge.xlsx"));
			wb.write(fos);
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}
}
