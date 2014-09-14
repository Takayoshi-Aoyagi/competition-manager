package com.tkdksc.excel.writer;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tkdksc.core.Category;
import com.tkdksc.core.Player;

public class AggregationSheetWriter extends AbstractExcelSheetWriter {

	private Category category;

	public AggregationSheetWriter(XSSFWorkbook wb, String key,
			Category category, XSSFCellStyle normalStyle,
			XSSFCellStyle titleStyle) {
		super(wb, key, normalStyle, titleStyle);
		this.category = category;
	}

	@Override
	protected void writeBody() {
		Map<String, List<Player>> map = category.getMap();
		for (String classification : map.keySet()) {
			if ("×".equals(classification)) {
				continue;
			}
			// header
			writeTableHeaderRow();
			nextLine();
			// body
			List<Player> players = map.get(classification);
			int numPlayer = 0;
			for (Player player : players) {
				numPlayer++;
				Object[] rowData = { numPlayer, classification,
						player.getName(), player.getKana(), player.getGrade(),
						player.getDojo() };
				writeTableBodyRow(rowData);
				nextLine();
			}
			nextLine();
		}
	}

	@Override
	protected void writeClassfiedTable() {
		// TODO Auto-generated method stub

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
		String[] headers = { "No.", "カテゴリー", "氏名", "かな", "級位・段位", "道場" };
		return headers;
	}
}
