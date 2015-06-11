package com.tkdksc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.arnx.jsonic.JSON;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tkdksc.core.AggregationGroup;
import com.tkdksc.utils.ExcelUtils;

public class TournamentExcelGeneratorMain {

	private XSSFWorkbook wb;

	public void execute() throws IOException {
		// copyTemplate();
		Map<String, List> tulMap = getMap(AggregationGroup.TUL);
		Map<String, List> massogiMap = getMap(AggregationGroup.MASSOGI);
		write2excel(tulMap, massogiMap);
	}

	private void write2excel(Map<String, List> tulMap, Map<String, List> massogiMap) throws IOException {
		FileOutputStream fos = new FileOutputStream(new File("data/excel/tournament_list.xlsx"));
		try {
			this.wb = new XSSFWorkbook();
			for (String key : tulMap.keySet()) {
				XSSFSheet sheet = wb.createSheet(String.format("%s %s", "トゥル", key));
				writeSheet(sheet, tulMap.get(key));
			}
			for (String key : massogiMap.keySet()) {
				XSSFSheet sheet = wb.createSheet(String.format("%s %s", "マッソギ", key));
				writeSheet(sheet, massogiMap.get(key));
			}
			wb.write(fos);
		} finally {
			fos.close();
		}
	}

	private void writeSheet(XSSFSheet sheet, List list) throws IOException {
		int size = list.size();
		int colno = 5;
		if (size < 2) {
			return;
		}
		//
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < 4; j++) {
				sheet.createRow(i * 4 + j);
			}
		}
		ExcelUtils.copyEdge(wb, sheet, size);

		for (int i = 0; i < size; i++) {
			Map player = (Map) list.get(i);
			String name = (String) player.get("name");
			String kana = (String) player.get("kana");
			String dojo = (String) player.get("dojo");
			BigDecimal seq = (BigDecimal) player.get("seq");
			String str1;
			String str2;
			if (seq == null) {
				str1 = "";
				str2 = "";
			} else {
				str1 = String.format("%s %s (%s)", seq, name, dojo);
				str2 = String.format("   %s", kana);
			}
			XSSFRow row1 = getRow(sheet, i * 4 + 1);
			XSSFCell cell1 = getCell(colno, row1);
			cell1.setCellValue(str1);
			XSSFRow row2 = getRow(sheet, i * 4 + 2);
			XSSFCell cell2 = getCell(colno, row2);
			cell2.setCellValue(str2);
		}
		ExcelUtils.copyEdge(wb, sheet, size);
	}

	private XSSFCell getCell(int colno, XSSFRow row) {
		XSSFCell cell = row.getCell(colno);
		if (cell == null) {
			cell = row.createCell(colno);
		}
		return cell;
	}

	private XSSFRow getRow(XSSFSheet sheet, int rowIndex) {
		XSSFRow row = sheet.getRow(rowIndex);
		if (row == null) {
			row = sheet.createRow(rowIndex);
		}
		return row;
	}

	private int getColNo(int size) {
		if (size == 16) {
			return 5;
		} else if (size == 8) {
			return 4;
		} else if (size == 4) {
			return 3;
		} else if (size == 2) {
			return 2;
		} else {
			return 1;
		}
	}

	private Map<String, List> getMap(AggregationGroup group) throws IOException {
		String type = group.name().toLowerCase();
		Map<String, List> map = new TreeMap<String, List>();
		String[] files = getFiles(type);
		for (String file : files) {
			List<Map> players = getPlayersFromFile(String.format("data/json/tournament/%s/%s", type, file));
			Map p0 = players.get(0);
			String clazz = (String) p0.get(type);
			map.put(clazz, players);
		}
		return map;
	}

	private static Integer getNumberFromFilename(String fname) {
		Pattern p = Pattern.compile("(\\d+)\\.json");
		Matcher m = p.matcher(fname);
		if (m.matches()) {
			int number = Integer.parseInt(m.group(1));
			return number;
		}
		return null;
	}

	private String[] getFiles(String type) {
		File tulDir = new File("data/json/tournament/" + type);
		String[] files = tulDir.list();
		Arrays.sort(files, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return getNumberFromFilename(o1) - getNumberFromFilename(o2);
			}
		});
		return files;
	}

	private List getPlayersFromFile(String path) throws IOException {
		Map json = getJsonFromFile(path);
		// System.out.println(json);
		List playerList = new ArrayList();
		List<Map> children = (List<Map>) json.get("children");
		getChildren(children, playerList);
		return playerList;
	}

	private void getChildren(List<Map> json, List childList) {
		for (Map child : json) {
			if (child == null) {
				continue;
			}
			List<Map> grandChild = (List<Map>) child.get("children");
			if (grandChild == null) {
				childList.add(child);
			} else {
				getChildren(grandChild, childList);
			}
		}
	}

	private Map getJsonFromFile(String path) throws IOException {
		byte[] fileContentBytes = Files.readAllBytes(Paths.get(path));
		// 読み込んだバイト列を UTF-8 でデコードして文字列にする
		String string = new String(fileContentBytes, StandardCharsets.UTF_8);
		Map json = JSON.decode(string);
		return json;
	}

	public static void main(String[] args) throws IOException {
		TournamentExcelGeneratorMain generator = new TournamentExcelGeneratorMain();
		generator.execute();
	}
}
