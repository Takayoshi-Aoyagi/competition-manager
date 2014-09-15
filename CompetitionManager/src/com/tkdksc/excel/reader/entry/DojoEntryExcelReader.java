package com.tkdksc.excel.reader.entry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tkdksc.core.Player;
import com.tkdksc.utils.StringUtils;

public class DojoEntryExcelReader {

	private List<Player> players = new ArrayList<Player>();
	
	public List<Player> readFiles() throws FileNotFoundException, IOException {
		File[] files = getFiles();
		for (File file : files) {
			readByFile(file);
		}
		return players;
	}

	private void readByFile(File file) throws FileNotFoundException,
			IOException {
		FileInputStream fis = new FileInputStream(file);
		try {
			readByWorkbook(fis);
		} finally {
			fis.close();
		}
	}

	private void readByWorkbook(FileInputStream fis) throws IOException {
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		int numSheets = wb.getNumberOfSheets();
		for (int i = 0; i < numSheets; i++) {
			readBySheet(wb, i);
		}
	}

	private void readBySheet(XSSFWorkbook wb, int i) {
		XSSFSheet sheet = wb.getSheetAt(i);
		Iterator<Row> rows = sheet.iterator();
		while (rows.hasNext()) {
			Row row = rows.next();
			Player player = readByRow(row);
			if (player == null) {
				continue;
			}
			players.add(player);
		}
	}

	private Player readByRow(Row row) {
		Cell cIndex = row.getCell(0);
		if (cIndex == null || cIndex.getCellType() != Cell.CELL_TYPE_NUMERIC) {
			return null;
		}
		String name = getCellString(row, 1);
		String grade = getCellString(row, 3);
		String dojo = getCellString(row, 4);
		String tul = getCellString(row, 5);
		String massogi = getCellString(row, 8);
		String special = getCellString(row, 12);
		String teamTul = getCellString(row, 14);
		String kana = getCellString(row, 16);
		String entryFee = getCellString(row, 18);
		if (StringUtils.isNullOrEmpty(name) || StringUtils.isNullOrEmpty(kana)) {
			return null;
		}
		Player player = new Player(name, grade, dojo, tul, massogi, special,
				teamTul, kana, entryFee);
		return player;
	}

	private String getCellString(Row row, int i) {
		Cell cell = row.getCell(i);
		if (cell == null) {
			return null;
		}
		String string = cell.getStringCellValue();
		return string.trim();
	}

	private File[] getFiles() {
		File inputDir = new File("input");
		File[] files = inputDir.listFiles();
		return files;
	}
}
