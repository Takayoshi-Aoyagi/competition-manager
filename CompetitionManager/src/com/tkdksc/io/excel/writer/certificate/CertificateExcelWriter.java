package com.tkdksc.io.excel.writer.certificate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tkdksc.core.Prize;

public class CertificateExcelWriter {

	private List<Prize> prizeList;

	public CertificateExcelWriter(List<Prize> prizeList) {
		this.prizeList = prizeList;
	}

	public void execute() throws IOException {
		FileOutputStream fos = new FileOutputStream(
				new File("output/certificate.xlsx"));
		try {
			XSSFWorkbook wb = new XSSFWorkbook();
			for (Prize prize : prizeList) {
				XSSFSheet sheet = wb.createSheet();
				writeRow(0, sheet,
						prize.getCategory() + " " + prize.getClassification());
				writeRow(1, sheet, prize.getRank());
				writeRow(2, sheet, prize.getName());
			}
			wb.write(fos);
		} finally {
			fos.close();
		}
	}

	private void writeRow(int rowNum, XSSFSheet sheet, String division) {
		XSSFRow row1 = sheet.createRow(rowNum);
		XSSFCell cell = row1.createCell(0);
		cell.setCellValue(division);
	}
}
