package com.tkdksc.excel.reader.entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class AbstractExcelReader {

	protected String getCellString(Row row, int i) {
		Cell cell = row.getCell(i);
		if (cell == null) {
			return null;
		}
		String string = cell.getStringCellValue();
		return string.trim();
	}

}
