package com.das.datadriven;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.das.pojo.FluoroFinder;
import com.das.validation.FlouroFinderPerformTasks;

@Component
public class DataDrivenExcel extends DataDrivenTest {

	private static ArrayList<String> columnName = new ArrayList<String>();

	private static ArrayList<FluoroFinder> details = new ArrayList<FluoroFinder>();

	private static int rows;

	private static int cols;

	public static int getRows() {
		return rows;
	}

	public static void setRows(int rows) {
		DataDrivenExcel.rows = rows;
	}

	public static int getCols() {
		return cols;
	}

	public static void setCols(int cols) {
		DataDrivenExcel.cols = cols;
	}

	public static ArrayList<String> getColumnName() {
		return columnName;
	}

	public static void setColumnName(ArrayList<String> columnName) {
		DataDrivenExcel.columnName = columnName;
	}

	public static ArrayList<FluoroFinder> getDetails() {
		return details;
	}

	public static void setDetails(ArrayList<FluoroFinder> details) {
		DataDrivenExcel.details = details;
	}

	public String[] fetchDataFromExcel() throws IOException {
		String pathToExcel = System.getProperty("user.dir") + "\\ExcellDocs\\ContentData.xlsx";
		FileInputStream fis = new FileInputStream(pathToExcel);
		String result = "";
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet("InputData");
		// We can make changes here if the requirement is to loop through multiple
		// sheets
		try {
			rows = sheet.getLastRowNum();
			cols = sheet.getRow(1).getLastCellNum();

			for (int i = 0; i <= rows; i++) {
				int count = 0;
				XSSFRow row = sheet.getRow(i);
				for (int j = 0; j < cols; j++) {
					if (count == 0) {
						result = result + "|";
					}
					XSSFCell cell = row.getCell(j);
					// first convert all the excel data to string
					if (cell.getCellType().name().equalsIgnoreCase("String")) {
						result = result + cell.getStringCellValue();
					} else if (cell.getCellType().name().equalsIgnoreCase("Numeric")) {
						result = result + String.valueOf(cell.getNumericCellValue());
					} else if (cell.getCellType().name().equalsIgnoreCase("Boolean")) {
						result = result + String.valueOf(cell.getBooleanCellValue());
					}

					result = result + "|";
					count++;
				}
				result = result + "\n";

			}

			System.out.println(result);
			result = result.substring(1, result.length() - 1);
			result = result.replaceAll("(\r\n|\n)", "");
			String temp[] = result.split("\\|");
			return temp;
		} catch (NullPointerException e) {
			System.out.println("You don't have any rows in the excel to work on");
		}
		return null;

	}

	public void printHashMapInExcel() throws IOException {
		// TODO Auto-generated method stub
		XSSFWorkbook workbook = new XSSFWorkbook();
		String filePath = System.getProperty("user.dir") + "\\ExcellDocs\\ResultPage.xlsx";
		FileOutputStream fos = new FileOutputStream(filePath);
		XSSFSheet sheet = workbook.createSheet("Details");
		Set<String> se = FlouroFinderPerformTasks.outerMap.keySet();
		Iterator<String> it = se.iterator();
		XSSFRow row = sheet.createRow(0);
		int p = 0;
		while (it.hasNext()) {
			XSSFCell cell;
			Object currentString = it.next();
			cell = row.createCell(p);
			String cellStringValue = currentString.toString();
			cell.setCellValue(cellStringValue);
			p++;
		}

		int i = 0;

		int startValue = getStartValue();
		int endValue = getEndValue();
		for (int k = 1; k <= (endValue - startValue) + 1; k++) {
			row = sheet.createRow(k);
			for (int j = 0; j < FlouroFinderPerformTasks.arrayList.size(); j++) {
				XSSFCell cell;
				cell = row.createCell(j);
				cell.setCellValue(FlouroFinderPerformTasks.arrayList.get(i));
				i++;
				if (j == FlouroFinderPerformTasks.outerMap.size() - 1) {
					break;
				}
			}
		}
		workbook.write(fos);
		System.out.println("Data added to the excel");
		fos.close();

	}

}
