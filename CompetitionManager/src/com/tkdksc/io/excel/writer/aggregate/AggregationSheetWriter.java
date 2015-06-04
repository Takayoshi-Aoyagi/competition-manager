package com.tkdksc.io.excel.writer.aggregate;

import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tkdksc.core.Category;
import com.tkdksc.core.Player;

public class AggregationSheetWriter extends AbstractExcelSheetWriter {

	private Map<String, List<Player>> map;

	public AggregationSheetWriter(XSSFWorkbook wb, String key, Category category, XSSFCellStyle normalStyle,
			XSSFCellStyle titleStyle) {
		super(wb, key, normalStyle, titleStyle);
		this.map = category.getMap();
	}

	@Override
	protected void writeBody() {
		for (String classification : map.keySet()) {
			if ("×".equals(classification)) {
				continue;
			}
			writeClassfiedTable(classification);
		}
	}

	@Override
	protected void writeClassfiedTable(String classification) {
		// header
		writeTableHeaderRow();
		nextLine();
		// body
		List<Player> players = map.get(classification);
		int numPlayer = 0;
		for (Player player : players) {
			numPlayer++;
			Object[] rowData = { numPlayer, classification, player.getSeq(), player.getName(), player.getKana(),
					player.getGrade(), player.getDojo() };
			writeTableBodyRow(rowData);
			nextLine();
		}
		nextLine();
	}

	@Override
	protected String[] getHeaderTitles() {
		String[] headers = { "No.", "カテゴリー", "ゼッケン", "氏名", "かな", "級位・段位", "道場" };
		return headers;
	}
}
