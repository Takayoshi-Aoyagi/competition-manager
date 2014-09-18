package com.tkdksc;

import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.tkdksc.core.Prize;
import com.tkdksc.io.excel.reader.entry.EntryListExcelReader;
import com.tkdksc.io.word.CertificateWordWriter;

public class CertificateGeneratorMain {

	public static void main(String[] args) throws IOException, InvalidFormatException {
		EntryListExcelReader er = new EntryListExcelReader("input/certificate/entry_list.xlsx");
		List<Prize> prizeList = er.execute();
		CertificateWordWriter cw = new CertificateWordWriter("output/certificate", prizeList);
		cw.execute();
	}
}
