package com.das.validation;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ThreadStabilization {

	public static String browser;

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		ThreadStabilization.browser = browser;
	}

	public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>() {
		protected WebDriver initialValue() {
			if (browser.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				return new FirefoxDriver();
			} else {
				WebDriverManager.chromedriver().setup();
				return new ChromeDriver();
			}
		}

		public WebDriver get() {
			return super.get();
		}

	};

	static int threadCountValue = 9;
	static BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(threadCountValue);

	// This part is enough to provide thread count based execution. We don't need to
	// worry about thread wait and execution it is been taken care by threadlocal.
	public synchronized void insertInBlockingQueue(String threadID) throws Exception {
		if (blockingQueue.size() <= threadCountValue) {
			blockingQueue.put(threadID);
			System.out.println("Added thread when blockingQueue size is ok");
			System.out.println(blockingQueue.size());

//		} else {
//			boolean decision = true;
//			System.out.println("Am I here");
//			while (decision) {
//				if (blockingQueue.size() < threadCountValue) {
//					blockingQueue.put(threadID);
//					decision = false;
//					System.out.println("Added thread when found space in the blockingQueue");
//					break;
//				}
//			}
//
//			System.out.println("You got to wait");
		}
	}

	public synchronized void removeFromBlockingQueue(String threadID) throws Exception {
		blockingQueue.remove(threadID);
	}
}