package com.tkdksc.io.word;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.tkdksc.core.Prize;
import com.tkdksc.utils.StringUtils;

public class CertificateWordWriter {

	private String outputDir;
	private List<Prize> prizeList;

	public CertificateWordWriter(String outputDir, List<Prize> prizeList) {
		this.prizeList = prizeList;
		this.outputDir = outputDir;
	}

	public void execute() throws IOException, InvalidFormatException {
		for (Prize prize : prizeList) {
			writeFile(prize);
		}
	}

	private void writeFile(Prize prize) throws IOException, InvalidFormatException {
		XWPFDocument doc = readTemplate();
		replaceText(prize, doc);
		writeFile(prize, doc);
	}

	private void writeFile(Prize prize, XWPFDocument doc) throws FileNotFoundException, IOException {
		String filename;
		Integer magic = prize.getMagic();
		if (magic == null) {
			filename = String.format("%s/%s%s-%s.docx", outputDir, prize.getCategory(),
					prize.getClassification(), prize.getRank());
		} else {
			filename = String.format("%s/%s%s-%s-%d.docx", outputDir, prize.getCategory(),
					prize.getClassification(), prize.getRank(), prize.getMagic());			
		}
		filename = StringUtils.noBlank(filename);
		FileOutputStream fos = new FileOutputStream(new File(filename));
		try {
			doc.write(fos);
		} finally {
			fos.close();
		}
	}

	private void replaceText(Prize prize, XWPFDocument doc) {
		for (XWPFParagraph p : doc.getParagraphs()) {
			for (XWPFRun r : p.getRuns()) {
				String text = r.getText(0);
				if (text == null) {
					continue;
				}
				if (text.indexOf("CLASSIFICATION") >= 0) {
					String classification = String.format("%s %s", prize.getCategory(),
							prize.getClassification());
					classification = StringUtils.noBlank(classification);
					r.setText(classification, 0);
				}
				if (text.indexOf("RANK") >= 0) {
					r.setText(prize.getRank(), 0);
				}
				if (text.indexOf("NAME") >= 0) {
					r.setText(String.format("%s 殿", prize.getName()), 0);
				}
			}
		}
	}

	private XWPFDocument readTemplate() throws IOException, InvalidFormatException {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String filename = String.format("template/certificate_template_%d.docx", year);
		XWPFDocument doc = new XWPFDocument(OPCPackage.open(filename));
		return doc;
	}
}
