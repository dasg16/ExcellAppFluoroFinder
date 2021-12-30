package com.das.datadriven;

import java.io.IOException;
import java.util.Arrays;

public abstract class DataDrivenTest {

	private static int startValue;
	private static int endValue;

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

	abstract String[] fetchRangeDataFromSource();

	abstract void setResultValuesBackToSource();

	public static String[][] mapRowDetailsInTwoDArray(String temp[], int rowCount, int columnCount,
			String lastRangeValue) throws IOException {

		String tempNew[] = new String[columnCount];
		if ((lastRangeValue.equalsIgnoreCase(""))) {
			lastRangeValue = "0.0";
		}

		int k = 0;
		for (int j = columnCount; j < temp.length; j++) {
			System.out.println(temp[j]);
			if ((temp[j].isEmpty())) {
				continue;
			}
			tempNew[k] = temp[j];
			k++;
		}

		int startValue = Integer.parseInt(tempNew[0].replace(".0", ""));
		int lastExeRangeValue = Integer.parseInt(lastRangeValue.replace(".0", ""));
		if (lastExeRangeValue >= startValue) {
			setStartValue(lastExeRangeValue + 1);
		} else {
			setStartValue(Integer.parseInt(tempNew[0].replace(".0", "")));
		}

		int rangeSize = Integer.parseInt(tempNew[columnCount - 1].replace(".0", ""));
		setEndValue(getStartValue() + rangeSize);
		System.out.println(getStartValue());
		System.out.println(getEndValue());

		int value = getStartValue();
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

}
