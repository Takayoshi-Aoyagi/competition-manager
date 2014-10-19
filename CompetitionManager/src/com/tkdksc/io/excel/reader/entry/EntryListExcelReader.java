package com.tkdksc.io.excel.reader.entry;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tkdksc.core.Prize;
import com.tkdksc.utils.StringUtils;

public class EntryListExcelReader extends AbstractExcelReader {

	private static final String MAGIC = "リザルト";

	private List<Prize> prizeList = new ArrayList<Prize>();
	private String inputFile;
	
	public EntryListExcelReader(String inputFile) {
		this.inputFile = inputFile;
	}

	public List<Prize> execute() throws IOException {
		FileInputStream fis = new FileInputStream(new File(inputFile));
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheet(MAGIC);
		Iterator<Row> it = sheet.iterator();
		while (it.hasNext()) {
			Row row = it.next();
			String category = getCellString(row, 0);
			String classification = getCellString(row, 1);
			String first = getCellString(row, 2);
			String second = getCellString(row, 3);
			String third1 = getCellString(row, 4);
			String third2 = getCellString(row, 5);
			if (MAGIC.equals(category) || "種目".equals(category)) {
				continue;
			}
			if (!StringUtils.isNullOrEmpty(first)) {
				prizeList.add(new Prize(category, classification, "優勝", first));
			}
			if (!StringUtils.isNullOrEmpty(second)) {
				prizeList.add(new Prize(category, classification, "準優勝", second));
			}
			if (!StringUtils.isNullOrEmpty(third1)) {
				prizeList.add(new Prize(category, classification, "第三位-1", third1));
			}
			if (!StringUtils.isNullOrEmpty(third2)) {
				prizeList.add(new Prize(category, classification, "第三位-2", third2));
			}
		}
		//
		for (Prize prize : prizeList) {
			System.out.println(prize);
		}
		return prizeList;
	}
}
