package com.das.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FluoroFinderPOM extends POMBase {

	FluoroFinderPOM fluoroFinderPOM;
	WebDriver driver;
	By suppProdLink = By.linkText("(View supplier product page)");
	By citation = By.xpath("//*[@id='link-to-references']");
	By supplier = By.xpath(getCommonXpath(1));
	By catalog = By.xpath(getCommonXpath(2));
	By size = By.xpath(getCommonXpath(3));
	By price = By.xpath(getCommonXpath(4));
	By antigen = By.xpath(getCommonXpath(5));
	By clone = By.xpath(getCommonXpath(6));
	By host = By.xpath(getCommonXpath(7));
	By isotype = By.xpath(getCommonXpath(8));
	By conjugate = By.xpath(getCommonXpath(9));
	By target = By.xpath(getCommonXpath(10));
	By applications = By.xpath(getCommonXpath(11));
	By description = By.xpath(getCommonXpath(12));

	public String getCommonXpath(int value) {
		return "//*[@id='top-container']//table/tbody/tr[" + value + "]/td[2]";

	}

	public FluoroFinderPOM(WebDriver driver) {
		this.driver = driver;
	}

//	public synchronized static void getInstance(WebDriver driver) {
//		if (fluoroFinderPOM == null) {
//
//			synchronized (DriverInitialization.class) {
//				if (fluoroFinderPOM == null) {
//					fluoroFinderPOM = new FluoroFinderPOM();
//					FluoroFinderPOM.driver = driver;
//				}
//			}
//		}
//
//	}

	public WebElement getCitation() {
		return driver.findElement(citation);
	}

	public WebElement getSupplier() {
		return driver.findElement(supplier);
	}

	public WebElement getCatalog() {
		return driver.findElement(catalog);
	}

	public WebElement getSize() {
		return driver.findElement(size);
	}

	public WebElement getPrice() {
		return driver.findElement(price);
	}

	public WebElement getAntigen() {
		return driver.findElement(antigen);
	}

	public WebElement getClone() {
		return driver.findElement(clone);
	}

	public WebElement getHost() {
		return driver.findElement(host);
	}

	public WebElement getIsotype() {
		return driver.findElement(isotype);
	}

	public WebElement getConjugate() {
		return driver.findElement(conjugate);
	}

	public WebElement getTarget() {
		return driver.findElement(target);
	}

	public WebElement getApplications() {
		return driver.findElement(applications);
	}

	public WebElement getDescription() {
		return driver.findElement(description);
	}

	public WebElement getSuppProdLink(String startValue) {
		return driver.findElement(suppProdLink);
	}

}
