package com.das.validation;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ThreadStabilization {

	public static String threadCount = System.getProperty("threads");

	public static String browser;

	private static ThreadStabilization instances;

	private ThreadStabilization() {

	}

	public static ThreadStabilization getInstance() {
		if (instances == null) {
			synchronized (ThreadStabilization.class) {
				if (instances == null) {
					instances = new ThreadStabilization();
				}
			}
		}
		return instances;
	}

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
				FirefoxOptions firefoxOption = new FirefoxOptions();
				firefoxOption.addArguments("-private");
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability(ChromeOptions.CAPABILITY, firefoxOption);
				return new FirefoxDriver(capabilities);
			} else {
				WebDriverManager.chromedriver().setup();
				ChromeOptions chromeOption = new ChromeOptions();
				chromeOption.addArguments("--incognito");
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOption);
				return new ChromeDriver(capabilities);
			}
		}

		public WebDriver get() {
			return super.get();
		}

	};

	// This part is enough to provide thread count based execution. We don't need to
	// worry about thread waits for available sort using concepts like ThreadPool.
	// ThreadLocal is taking care of it in the background if the setup is correct.

	static BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(Integer.valueOf(threadCount));

	public synchronized void insertInBlockingQueue(String threadID) throws Exception {
		if (blockingQueue.size() <= Integer.valueOf(threadCount)) {
			blockingQueue.put(threadID);
			System.out.println("Added thread when blockingQueue size is ok");
			System.out.println(blockingQueue.size());
		}
	}

	public void removeFromBlockingQueue(String threadID) throws Exception {
		blockingQueue.remove(threadID);
	}
}