package com.tkdksc.excel.writer;

import java.util.ArrayList;
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
				writeTableBodyRow(classification, player, numPlayer);
				nextLine();
			}
			nextLine();
		}
	}

	@Override
	protected void writeClassfiedTable() {
		// TODO Auto-generated method stub
		
	}

	private void writeTableBodyRow(String division, Player player, int numPlayer) {
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

	@Override
	protected String[] getHeaderTitles() {
		String[] headers = { "No.", "カテゴリー", "氏名", "かな", "級位・段位", "道場" };
		return headers;
	}
}
