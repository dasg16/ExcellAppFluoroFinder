package com.das.validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.das.pojo.FluoroFinder;
import com.das.pom.FluoroFinderPOM;
/*
 * Author: Das. This Class basically goes through parallel threads in testNG DataProvider. 
 * It uses ThreadStabilization class to maintain the memory resources among each Threads. 
 * For that I am using ThreadLocal which provides separate resources for each threads 
 * and in return we get good synchronization.
 * LinkedHashMap implementation has been used below to maintain the insertion order in the MAP
 */

public class FlouroFinderPerformTasks {

	public static ArrayList<FluoroFinder> details = new ArrayList<FluoroFinder>();
	public static ArrayList<String> arrayList = new ArrayList<String>();
	public static LinkedHashMap<String, String> outerMap = new LinkedHashMap<String, String>();

	private String appUrl;

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	// LinkedHashMap Implementation below
	public ArrayList<String> run(String startValue) {
		// TODO Auto-generated method stub
		FluoroFinderPOM fluoroFinderPOM;

		ThreadStabilization threadStabilization = ThreadStabilization.getInstance();
		final WebDriver driver = threadStabilization.driver.get();

		System.out.println("Thread ID for " + startValue + " is " + Thread.currentThread().getId());
		try {
			threadStabilization.insertInBlockingQueue(Thread.currentThread().getName());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			driver.navigate().to(appUrl + startValue);
			WebDriverWait wait = new WebDriverWait(driver, 40);
			wait.until(ExpectedConditions.urlToBe(appUrl + startValue));

		} catch (Exception e) {
			System.out.println("Page not loaded. Skip page!");
		}

		fluoroFinderPOM = new FluoroFinderPOM(driver);
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("ID Number", startValue);

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

			fluoroFinderPOM.getSuppProdLink(startValue).click();
			Set<String> abc = driver.getWindowHandles();
			Iterator<String> it = abc.iterator();
			String parentID = it.next();
			String childID = it.next();
			driver.switchTo().window(childID);
			// Just getting the URL so no need to wait for page to load
			map.put("View Supplier Product Link", driver.getCurrentUrl());
			driver.switchTo().window(parentID);
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
		threadStabilization.driver.remove();
		driver.quit();
		return arrayList;

	}

}
