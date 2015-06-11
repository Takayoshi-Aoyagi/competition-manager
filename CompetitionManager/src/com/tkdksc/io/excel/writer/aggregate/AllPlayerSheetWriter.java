package com.tkdksc.io.excel.writer.aggregate;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tkdksc.core.Player;

public class AllPlayerSheetWriter extends AbstractExcelSheetWriter {

	private List<Player> playerList;

	protected AllPlayerSheetWriter(XSSFWorkbook wb, String title, XSSFCellStyle normalStyle,
			XSSFCellStyle titleStyle, List<Player> playerList) {
		super(wb, title, normalStyle, titleStyle);
		this.playerList = playerList;
	}

	private void writeRow(XSSFRow row, Player player) {
		Object[] values = { player.getSeq(), player.getName(), player.getKana(), player.getGrade(),
				player.getDojo(), player.getTul(), player.getMassogi(), player.getSpecial() };
		for (int i = 0, max = values.length; i < max; i++) {
			XSSFCell cell = row.createCell(i);
			Object value = values[i];
			if (value instanceof String) {
				cell.setCellValue((String) value);
			} else if (value instanceof Integer) {
				cell.setCellValue(((Integer) value).doubleValue());
			}
		}
	}

	@Override
	protected void writeBody() {
		for (int i = 0, max = playerList.size(); i < max; i++) {
			Player player = playerList.get(i);
			XSSFRow row = sheet.createRow(i);
			writeRow(row, player);
		}
	}

	@Override
	protected String[] getHeaderTitles() {
		String[] titles = {"ゼッケン","氏名","カナ","級位","所属","トゥル","マッソギ","スペシャル"};
		return titles;
	}

	@Override
	protected void writeClassfiedTable(String classification) {
		// header
		writeTableHeaderRow();
		nextLine();
		// body
		for (Player player : this.playerList) {
			Object[] rowData = { player.getSeq(), player.getName(), player.getKana(), player.getGrade(),
					player.getDojo(), player.getTul(), player.getMassogi(), player.getSpecial() };
			writeTableBodyRow(rowData);
			nextLine();
		}
		nextLine();
	}
}
