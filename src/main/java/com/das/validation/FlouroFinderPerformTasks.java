package com.das.validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import org.openqa.selenium.WebDriver;

import com.das.pojo.FluoroFinder;
import com.das.pom.FluoroFinderPOM;

public class FlouroFinderPerformTasks {

	public static ArrayList<FluoroFinder> details = new ArrayList<FluoroFinder>();
	public static ArrayList<String> arrayList = new ArrayList<String>();
	public static LinkedHashMap<String, String> outerMap = new LinkedHashMap<String, String>();

//	public LinkedHashMap<String, String> getOuterMap() {
//		return outerMap;
//	}

	private String appUrl;

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	// LinkedHashMap Implementation below
	public ArrayList<String> run(String startValue) throws Exception {
		// TODO Auto-generated method stub
		FluoroFinderPOM fluoroFinderPOM;

		ThreadStabilization threadStabilization = new ThreadStabilization();
		System.out.println("Thread ID for " + startValue + " is " + Thread.currentThread().getId());
		// threadStabilization.insertInBlockingQueue(Thread.currentThread().getName());
		final WebDriver driver = ThreadStabilization.driver.get();

		driver.navigate().to(appUrl + startValue);

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
		ThreadStabilization.driver.remove();
		driver.quit();
		return arrayList;

	}

//	public void printHashMapInExcel() throws IOException {
//		// TODO Auto-generated method stub
//		XSSFWorkbook workbook = new XSSFWorkbook();
//		String filePath = System.getProperty("user.dir") + "\\ExcellDocs\\ResultPage.xlsx";
//		FileOutputStream fos = new FileOutputStream(filePath);
//		XSSFSheet sheet = workbook.createSheet("Details");
//		Set<String> se = outerMap.keySet();
//		Iterator<String> it = se.iterator();
//		XSSFRow row = sheet.createRow(0);
//		int p = 0;
//		while (it.hasNext()) {
//			XSSFCell cell;
//			Object currentString = it.next();
//			cell = row.createCell(p);
//			String cellStringValue = currentString.toString();
//			cell.setCellValue(cellStringValue);
//			p++;
//		}
//
//		int i = 0;
//
//		int startValue = getStartValue();
//		int endValue = getEndValue();
//		for (int k = 1; k <= (endValue - startValue) + 1; k++) {
//			row = sheet.createRow(k);
//			for (int j = 0; j < arrayList.size(); j++) {
//				XSSFCell cell;
//				cell = row.createCell(j);
//				cell.setCellValue(arrayList.get(i));
//				i++;
//				if (j == outerMap.size() - 1) {
//					break;
//				}
//			}
//		}
//		workbook.write(fos);
//		System.out.println("Data added to the excel");
//		fos.close();
//
//	}

}
