package com.das.test;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.das.datadriven.DataDrivenExcel;
import com.das.datadriven.DataDrivenTest;
import com.das.validation.FlouroFinderPerformTasks;

@ComponentScan({ "com.das.datadriven", "com.das.validation", "com.das.common" })
public class PrintFluoroFinderTest {
	public static String[][] twoDArray;
	public static DataDrivenTest dataDrivenTest;
	public static DataDrivenExcel dataDrivenExcel;
	public static ArrayList<String> columnName;
	public static ApplicationContext applicationContext;
	public static String temp[];

	@BeforeClass
	public void setLaunchActivities() throws IOException {
		applicationContext = new ClassPathXmlApplicationContext("spring.xml");

		var dataDrivenExcel = (DataDrivenExcel) applicationContext.getBean("DataDrivenExcel");
		if (dataDrivenExcel.fetchDataFromExcel() != null) {
			temp = dataDrivenExcel.fetchDataFromExcel();
			var dataDrivenTest = (DataDrivenTest) applicationContext.getBean("DataDrivenTest");
			columnName = dataDrivenTest.addColumneNamesAndCountRestOfRows(temp, dataDrivenExcel.rows,
					dataDrivenExcel.cols, dataDrivenExcel.columnName);
//			var webDriverLibrary = (WebDriverLibrary) applicationContext.getBean("WebDriverLibrary");
			twoDArray = dataDrivenTest.mapRowDetailsInTwoDArray(temp, dataDrivenExcel.rows,
					dataDrivenTest.rowCountTempArray, dataDrivenExcel.cols);
		}

	}

	@Test(dataProvider = "getData")
	public void performParallelTask(String empty, String rangeValue) throws Exception {
		var flouroFinderPerformTasks = (FlouroFinderPerformTasks) applicationContext
				.getBean("FlouroFinderPerformTasks");
		flouroFinderPerformTasks.run(rangeValue);
	}

	@AfterClass
	public void tearDownActivities() throws IOException {
		var dataDrivenExcel = (DataDrivenExcel) applicationContext.getBean("DataDrivenExcel");
		dataDrivenExcel.printHashMapInExcel();
	}

	@DataProvider(parallel = true)
	public Object[][] getData() {
		return twoDArray;

	}
}
