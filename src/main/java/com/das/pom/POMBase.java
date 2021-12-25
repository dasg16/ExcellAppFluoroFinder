package com.das.pom;

import java.util.NoSuchElementException;

/*
 * Here we can mention massive number of generic method that we use to identify elements 
 * on a webpage, tables, navigation or any other common structures among different webpages.
 */

import org.openqa.selenium.WebElement;

abstract class POMBase {
	public static boolean isElementExists(WebElement element) {
		try {
			element.getSize();
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}

}
