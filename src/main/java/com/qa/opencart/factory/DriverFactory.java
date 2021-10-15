package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	/**
	 * @author Dell This method is used to initialize the webdriver on the basis
	 *         of given browser name....
	 * @param browser
	 * @return this method will return driver
	 */

	public WebDriver driver;
	public Properties prop;
	public static String highlight;
	public OptionsManager optionsManager;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	public WebDriver initDriver(Properties prop) {

		optionsManager = new OptionsManager(prop);
		highlight = prop.getProperty("highlight");

		String browser = prop.getProperty("browser");
		System.out.println("Browser name is " + browser);

		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			// driver = new ChromeDriver(optionsManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));

		} else if (browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));

		} else {
			System.out.println("please pass the correct browser name..." + browser);
		}

		getDriver().get(prop.getProperty("url"));
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();

		return getDriver();

	}

	public synchronized WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * this method is used to initliaze the properties on the basis of given
	 * environment
	 * 
	 * @return this method return prop
	 */
	public Properties initProp() {

		Properties prop = new Properties();
		FileInputStream ip = null;
		String env = System.getProperty("env");

		if (env == null) {

			try {
				ip = new FileInputStream(".\\src\\test\\resources\\config\\config.properties");

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		else {
			System.out.println("Running on enviroment : " + env);
			try {

				switch (env.toLowerCase()) {

				case "qa":
					ip = new FileInputStream(".\\src\\test\\resources\\config\\qa.config.properties");
					break;

				case "stage":
					ip = new FileInputStream(".\\src\\test\\resources\\config\\stage.config.properties");
					break;

				case "dev":
					ip = new FileInputStream(".\\src\\test\\resources\\config\\dev.config.properties");
					break;

				default:
					System.out.println(".....Please pass the right environment....." + env);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}
	
	
	
	public String getScreenshot() {
		File srcFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir")+"/screenshot/"+System.currentTimeMillis()+".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	
}
