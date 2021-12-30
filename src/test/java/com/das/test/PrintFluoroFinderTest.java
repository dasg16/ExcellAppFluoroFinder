package com.das.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.das.datadriven.DataDrivenExcel;
import com.das.datadriven.DataDrivenTest;
import com.das.validation.FlouroFinderPerformTasks;

public class PrintFluoroFinderTest {
	public static String[][] twoDArray;
	public static ApplicationContext applicationContext;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	@BeforeClass
	public void setLaunchActivities() throws IOException {

		System.out.println("Start time " + getCurrentDateTime());
		applicationContext = new ClassPathXmlApplicationContext("spring.xml");
		var dataDrivenExcel = (DataDrivenExcel) applicationContext.getBean("DataDrivenExcel");
		String temp[] = dataDrivenExcel.fetchRangeDataFromSource();
		String lastRangeValue = dataDrivenExcel.fetchLastRangeValueFromSource();
		if (temp != null) {
			twoDArray = DataDrivenTest.mapRowDetailsInTwoDArray(temp, DataDrivenExcel.getRows(),
					DataDrivenExcel.getCols(), lastRangeValue);
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
		dataDrivenExcel.setResultValuesBackToSource();
		dataDrivenExcel.setLastIndexOfRangeToSource();
		System.out.println("End time " + getCurrentDateTime());

	}

	@DataProvider(parallel = true)
	public Object[][] getData() {
		return twoDArray;

	}

	public String getCurrentDateTime() throws IOException {
		Date now = new Date();
		String strDate = sdf.format(now);
		return strDate;

	}

}
