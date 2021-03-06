package com.das.validation;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.das.datadriven.DataDrivenExcel;
import com.das.pojo.FluoroFinder;
import com.das.pom.FluoroFinderPOM;
/*
 * Author: Das. This Class basically goes through parallel threads in testNG DataProvider. 
 * It uses ThreadStabilization class to maintain the memory resources among each Threads. 
 * For that I am using ThreadLocal which provides separate resources for each threads 
 * and in return we get good synchronization.
 */

public class FlouroFinderPerformTasks extends DataDrivenExcel {

	public static ArrayList<FluoroFinder> details = new ArrayList<FluoroFinder>();
	public static ArrayList<String> arrayList = new ArrayList<String>();
	public static LinkedHashMap<String, String> outerMap = new LinkedHashMap<String, String>();

	private String appUrl;

	public static boolean lastRangeValueAdded = false;

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public ArrayList<String> run(String loopValue) {
		// TODO Auto-generated method stub
		FluoroFinderPOM fluoroFinderPOM;

		ThreadStabilization threadStabilization = ThreadStabilization.getInstance();

		System.out.println("Thread ID for " + loopValue + " is " + Thread.currentThread().getId());
		try {
			threadStabilization.insertInBlockingQueue(Thread.currentThread().getName());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		final WebDriver driver = ThreadStabilization.driver.get();

		try {
			driver.navigate().to(appUrl + loopValue);
			WebDriverWait wait = new WebDriverWait(driver, 40);
			wait.until(ExpectedConditions.urlToBe(appUrl + loopValue));

		} catch (Exception e) {
			System.out.println("Page not loaded. Skip page!");
		}

		fluoroFinderPOM = new FluoroFinderPOM(driver);
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("ID Number", loopValue);

		try {
			map.put("Citations Count", fluoroFinderPOM.getCitation().getText());
		} catch (Exception e) {
			map.put("Citations Count", "Not Available");
		}
		try {
			map.put("Supplier", fluoroFinderPOM.getSupplier().getText());
		} catch (Exception e) {
			map.put("Supplier", "Not Available");
		}
		try {
			map.put("Catalog", fluoroFinderPOM.getCatalog().getText());
		} catch (Exception e) {
			map.put("Catalog", "Not Available");
		}
		try {
			map.put("Size", fluoroFinderPOM.getSize().getText());
		} catch (Exception e) {
			map.put("Size", "Not Available");
		}
		try {
			map.put("Price", fluoroFinderPOM.getPrice().getText());
		} catch (Exception e) {
			map.put("Price", "Not Available");
		}
		try {
			map.put("Antigen", fluoroFinderPOM.getAntigen().getText());
		} catch (Exception e) {
			map.put("Antigen", "Not Available");
		}

		try {
			map.put("Clone", fluoroFinderPOM.getClone().getText());
		} catch (Exception e) {
			map.put("Clone", "Not Available");
		}

		try {
			map.put("Host", fluoroFinderPOM.getHost().getText());
		} catch (Exception e) {
			map.put("Host", "Not Available");
		}

		try {
			map.put("Isotype", fluoroFinderPOM.getIsotype().getText());
		} catch (Exception e) {
			map.put("Isotype", "Not Available");
		}

		try {
			map.put("Conjugate", fluoroFinderPOM.getConjugate().getText());
		} catch (Exception e) {
			map.put("Conjugate", "Not Available");
		}

		try {
			map.put("Target Species", fluoroFinderPOM.getTarget().getText());
		} catch (Exception e) {
			map.put("Target Species", "Not Available");
		}

		try {
			map.put("Application", fluoroFinderPOM.getApplications().getText());
		} catch (Exception e) {
			map.put("Application", "Not Available");
		}

		try {
			map.put("Description", fluoroFinderPOM.getDescription().getText());
		} catch (Exception e) {
			map.put("Description", "Not Available");
		}

		try {
			map.put("View Supplier Product Link", fluoroFinderPOM.getSuppProdLink().getAttribute("href"));
		} catch (Exception e) {
			map.put("View Supplier Product Link", "Not Available");
		}

		arrayList.addAll(map.values());
		outerMap.putAll(map);
		try {
			threadStabilization.removeFromBlockingQueue(Thread.currentThread().getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (loopValue.equalsIgnoreCase(String.valueOf(getEndValue()))) {
			lastRangeValueAdded = true;
		}
		threadStabilization.driver.remove();
		driver.quit();
		return arrayList;

	}

}
