package com.das.test;

import java.io.IOException;

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
	public static ApplicationContext applicationContext;

	@BeforeClass
	public void setLaunchActivities() throws IOException {
		System.out.println("Start time " + System.currentTimeMillis());

		applicationContext = new ClassPathXmlApplicationContext("spring.xml");

		var dataDrivenExcel = (DataDrivenExcel) applicationContext.getBean("DataDrivenExcel");
		String temp[] = dataDrivenExcel.fetchDataFromExcel();
		if (temp != null) {
			var dataDrivenTest = (DataDrivenTest) applicationContext.getBean("DataDrivenTest");
			dataDrivenTest.addColumneNamesAndCountRestOfRows(temp, DataDrivenExcel.getRows(), DataDrivenExcel.getCols(),
					DataDrivenExcel.getColumnName());
			twoDArray = DataDrivenTest.mapRowDetailsInTwoDArray(temp, DataDrivenExcel.getRows(),
					DataDrivenTest.getRowCountTempArray(), DataDrivenExcel.getCols());
		}

	}

	@Test(dataProvider = "getData")
	public void performParallelTask(String empty, String rangeValue) {
		var flouroFinderPerformTasks = (FlouroFinderPerformTasks) applicationContext
				.getBean("FlouroFinderPerformTasks");
		flouroFinderPerformTasks.run(rangeValue);
	}

	@AfterClass
	public void tearDownActivities() throws IOException {
		var dataDrivenExcel = (DataDrivenExcel) applicationContext.getBean("DataDrivenExcel");
		dataDrivenExcel.printHashMapInExcel();
		System.out.println("End time " + System.currentTimeMillis());
	}

	@DataProvider(parallel = true)
	public Object[][] getData() {
		return twoDArray;

	}
}
