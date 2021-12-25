package com.das.datadriven;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Component;

@Component
public class DataDrivenTest {

	private static int startValue;
	private static int endValue;
	public static int rowCountTempArray;

	public static int getStartValue() {
		return startValue;
	}

	public static void setStartValue(int startValue) {
		DataDrivenTest.startValue = startValue;
	}

	public static int getEndValue() {
		return endValue;
	}

	public static void setEndValue(int endValue) {
		DataDrivenTest.endValue = endValue;
	}

	public DataDrivenTest() {
		rowCountTempArray = 0;
	}

	/*
	 * Author: Das. These methods below could be used to transform data from Excel
	 * or any other source to ArrayList of POJO Objects.
	 */
	public ArrayList<String> addColumneNamesAndCountRestOfRows(String result[], int rows, int cols,
			ArrayList<String> columnName) throws IOException {
		System.out.println(Arrays.toString(result));
		// Getting the column names
		for (int k = 0; k < cols; k++) {
			System.out.println(result[k]);
			columnName.add(result[k]);
		}
		// Getting rest of the rows count

		for (int j = cols; j < result.length; j++) {
			System.out.println(result[j]);
			if ((result[j].isEmpty())) {
				continue;
			}
			rowCountTempArray++;
		}
		// mapDataInArrayList(result, cols, rowCountTempArray, rows, columnName);
		System.out.println(columnName.toString());
		return columnName;

	}

	/*
	 * Author: Das This method is for getting the start value and the end value of
	 * the range and transform the range into a 2D Array so that we can use it in
	 * DataProvider
	 */
	public static String[][] mapRowDetailsInTwoDArray(String temp[], int rowCount, int rowCountTempArray,
			int columnCount) throws IOException {
		String tempNew[] = new String[rowCountTempArray];

		int k = 0;
		for (int j = columnCount; j < temp.length; j++) {
			System.out.println(temp[j]);
			if ((temp[j].isEmpty())) {
				continue;
			}
			tempNew[k] = temp[j];
			k++;
		}

		setStartValue(Integer.parseInt(tempNew[0].replace(".0", "")));
		setEndValue(Integer.parseInt(tempNew[1].replace(".0", "")));
		System.out.println(startValue);
		System.out.println(endValue);

		int rangeSize = endValue - startValue;
		int value = startValue;
		System.out.println(Arrays.toString(tempNew));
		String tempRows[][] = new String[rangeSize + 1][columnCount];
		for (int j = 0; j < (rangeSize + 1); j++) {
			tempRows[j][0] = "";
			tempRows[j][1] = String.valueOf(value);
			value++;

		}

		System.out.println(Arrays.deepToString(tempRows));
		return tempRows;

	}

//	public abstract void setTestDataInPojo(String[][] tempRows, int columnCount);
//	public abstract void getTestDataFromPojo(String[][] tempRows, int columnCount, int rowCount,ArrayList<String> columnName) throws IOException;

}
