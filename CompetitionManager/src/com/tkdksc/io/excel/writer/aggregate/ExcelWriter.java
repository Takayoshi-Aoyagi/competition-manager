package com.tkdksc.io.excel.writer.aggregate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tkdksc.core.Category;
import com.tkdksc.core.Player;

public class ExcelWriter {

	private XSSFCellStyle titleStyle;
	private XSSFCellStyle normalStyle;
	private String outputDir;
	private Map<String, Category> categoryMap;
	private List<Player> playerList;
	
	public ExcelWriter(List<Player> playerList, Map<String, Category> categoryMap, String outputDir) {
		this.playerList = playerList;
		this.categoryMap = categoryMap;
		this.outputDir = outputDir;
	}

	public void write2excel() throws IOException {
		FileOutputStream fos = new FileOutputStream(new File(outputDir + "/entry_list.xlsx"));
		try {
			XSSFWorkbook wb = new XSSFWorkbook();
			initStyles(wb);
			new AllPlayerSheetWriter(wb, "全体", normalStyle, titleStyle, playerList).write();
			for (String key : categoryMap.keySet()) {
				Category category = categoryMap.get(key);
				new AggregationSheetWriter(wb, key, category, normalStyle, titleStyle).write();
			}
			new ResultSheetWriter(wb, categoryMap, normalStyle, titleStyle).write();
			wb.write(fos);
		} finally {
			fos.close();
		}
	}

	private void initStyles(XSSFWorkbook wb) {
		titleStyle = wb.createCellStyle();
		titleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		titleStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
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
}
