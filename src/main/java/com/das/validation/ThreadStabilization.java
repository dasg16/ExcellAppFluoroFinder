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

	// The runtime processor will get us the correct core counts and based on that
	// we should create our number of threads in a CPU intensive task. Otherwise it
	// will slack in terms of performance. Here you also need to consider CPU core
	// using any other program outside your execution in the background
	int coreCount = Runtime.getRuntime().availableProcessors();
	// For our case it is not a CPU intensive task so we will use manual thread
	// counts
//	public ExecutorService service = Executors.newFixedThreadPool(2);
//
//	public void setThreadPoolService() {
//		// TODO Auto-generated method stub
//		service.execute(new FlouroFinderPerformTasks());
//	}

	static BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(10);

	public void insertInBlockingQueue(String threadID) throws Exception {
		if (blockingQueue.size() <= 10) {
			blockingQueue.put(threadID);
		} else {
			boolean decision = true;
			while (decision) {
				if (blockingQueue.size() < 10) {
					blockingQueue.put(threadID);
					decision = false;
					System.out.println("Added thread when found space in the blockingQueue");
					break;
				}
			}

			System.out.println("You got to wait");
		}
	}

	public void removeFromBlockingQueue(String threadID) throws Exception {
		System.out.println("Thread name to remove " + threadID);
		System.out.println("Before removing " + blockingQueue.size());
		blockingQueue.remove(threadID);
		System.out.println("After removing " + blockingQueue.size());
		System.out.println("Thread name removed " + threadID);
	}
}