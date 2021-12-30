package com.das.datadriven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.das.validation.FlouroFinderPerformTasks;

public class DataDrivenExcel extends DataDrivenTest {

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

	public String[] fetchRangeDataFromSource() {
		String pathToExcel = System.getProperty("user.dir") + "\\ExcellDocs\\ContentData.xlsx";

		try {
			FileInputStream fis = new FileInputStream(pathToExcel);
			String result = "";
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheet("InputData");

			setRows(sheet.getLastRowNum());
			setCols(sheet.getRow(1).getLastCellNum());

			for (int i = 0; i <= rows; i++) {
				int count = 0;
				XSSFRow row = sheet.getRow(i);
				for (int j = 0; j < getCols(); j++) {
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
		} catch (IOException e) {
			System.out.println("You have an exception- " + e);
		}
		return null;

	}

	public String fetchLastRangeValueFromSource() {
		String resultRangeValue = "";

		File filePath = new File(System.getProperty("user.dir") + "\\ExcellDocs\\LastRangeNumber.xlsx");
		if (filePath.exists()) {
			try {
				FileInputStream fisRangeValue = new FileInputStream(filePath);
				XSSFWorkbook workbookRangeValue = new XSSFWorkbook(fisRangeValue);
				XSSFSheet sheetRangeValue = workbookRangeValue.getSheet("Details");
				try {
					XSSFRow rowRangeValue = sheetRangeValue.getRow(sheetRangeValue.getLastRowNum());
					XSSFCell cellRangeValue = rowRangeValue.getCell(sheetRangeValue.getLastRowNum());

					if (cellRangeValue.getCellType().name().equalsIgnoreCase("String")) {
						resultRangeValue = resultRangeValue + cellRangeValue.getStringCellValue();
					} else if (cellRangeValue.getCellType().name().equalsIgnoreCase("Numeric")) {
						resultRangeValue = resultRangeValue + String.valueOf(cellRangeValue.getNumericCellValue());
					} else if (cellRangeValue.getCellType().name().equalsIgnoreCase("Boolean")) {
						resultRangeValue = resultRangeValue + String.valueOf(cellRangeValue.getBooleanCellValue());
					}
				}

				catch (NullPointerException e) {
					System.out.println("You don't have any rows in the excel to work on");
				}
			} catch (Exception e) {
				System.out.println("You have an exception- " + e);
			}
		}
		return resultRangeValue;
	}

	public void setResultValuesBackToSource() {
		// TODO Auto-generated method stub

		File filePath = new File(System.getProperty("user.dir") + "\\ExcellDocs\\Results.xlsx");
		try {
			if (filePath.exists()) {

				System.out.println("FilePath already exists");
				FileInputStream fis = new FileInputStream(filePath);
				XSSFWorkbook workbook = new XSSFWorkbook(fis);
				XSSFSheet sheet = workbook.getSheet("Details");
				FileOutputStream fos = new FileOutputStream(filePath);
				setRows(sheet.getLastRowNum() + 1);
				System.out.println("Rows in excel before adding " + getRows());
				int i = 0;

				int startValue = getStartValue();
				int endValue = getEndValue();
				for (int k = getRows(); k <= getRows() + (endValue - startValue); k++) {
					XSSFRow row = sheet.createRow(k);
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
				setRows(sheet.getLastRowNum());
				System.out.println("Rows in excel after adding " + getRows());
				workbook.write(fos);
				System.out.println("Data added to the excel");
				fos.close();

			} else {

				XSSFWorkbook workbook = new XSSFWorkbook();
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

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("You have an exception- " + e);
		}

	}

	public void setLastIndexOfRangeToSource() {
		// TODO Auto-generated method stub
		File filePath = new File(System.getProperty("user.dir") + "\\ExcellDocs\\LastRangeNumber.xlsx");
		try {
			if (filePath.exists()) {
				if (FlouroFinderPerformTasks.lastRangeValueAdded) {
					System.out.println("FilePath already exists");
					FileInputStream fis = new FileInputStream(filePath);
					XSSFWorkbook workbook = new XSSFWorkbook(fis);
					XSSFSheet sheet = workbook.getSheet("Details");
					DataDrivenExcel.excelWrittingRangeValue(workbook, filePath, sheet);
				}
			} else {
				if (FlouroFinderPerformTasks.lastRangeValueAdded) {
					XSSFWorkbook workbook = new XSSFWorkbook();
					XSSFSheet sheet = workbook.createSheet("Details");
					DataDrivenExcel.excelWrittingRangeValue(workbook, filePath, sheet);

				}
			}
		} catch (Exception e) {
			System.out.println("You have an exception- " + e);
		}

	}

	public static void excelWrittingRangeValue(XSSFWorkbook workbook, File filePath, XSSFSheet sheet)
			throws IOException {
		FileOutputStream fos = new FileOutputStream(filePath);

		XSSFRow row = sheet.createRow(0);
		XSSFCell cell = row.createCell(0);
		cell.setCellValue(getEndValue());
		workbook.write(fos);
		System.out.println("Last Range Value added to the excel");
		fos.close();
	}

}
