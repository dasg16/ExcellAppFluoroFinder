package com.das.pom;

import java.util.NoSuchElementException;

import org.openqa.selenium.WebElement;

abstract class POMBase {
	public static boolean isExistsElement(WebElement element) {
		try {
			element.getSize();
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}

}
