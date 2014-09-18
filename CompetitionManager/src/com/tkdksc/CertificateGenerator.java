package com.tkdksc;

import java.io.IOException;
import java.util.List;

import com.tkdksc.core.Prize;
import com.tkdksc.io.excel.reader.entry.EntryListExcelReader;
import com.tkdksc.io.excel.writer.certificate.CertificateExcelWriter;

public class CertificateGenerator {

	public static void main(String[] args) throws IOException {
		EntryListExcelReader er = new EntryListExcelReader();
		List<Prize> prizeList = er.execute();
		CertificateExcelWriter cw = new CertificateExcelWriter(prizeList);
		cw.execute();
	}
}
