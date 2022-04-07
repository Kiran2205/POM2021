package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * 
 * @author kiranmallappa
 *
 */
public class DriverFactory {
	WebDriver driver;
	Properties prop;
	
	private OptionsManager optionManager;
	public static String highlight;
	//Introducing concept of thread local
	public static ThreadLocal<WebDriver> tldriver = new ThreadLocal<>();
	
	/**
	 * This method is used to create a driver instance based on the browser name
	 * @param prop
	 * @return {@link WebDriver}
	 */
	public WebDriver init_driver(Properties prop) {
		highlight = prop.getProperty("highlight");
		optionManager = new OptionsManager(prop);
		
		String browserName = prop.getProperty("browser").trim();
		String browserVersion = prop.getProperty("browserversion").trim();
		System.out.println("Browser name is "+browserName);
		
		switch (browserName.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromiumdriver().setup();
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteDriver("chrome", browserVersion);
			} else {
//				driver = new ChromeDriver(optionManager.getChromeOptions());
				tldriver.set(new ChromeDriver(optionManager.getChromeOptions()));
			}
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteDriver("firefox", browserVersion);
			} else {
//				driver = new FirefoxDriver(optionManager.getFirefoxOptions());
				tldriver.set(new FirefoxDriver(optionManager.getFirefoxOptions()));
			}
			break;
		case "safari":
//			driver = new SafariDriver();
			tldriver.set(new SafariDriver());
			break;

		default:
			System.out.println("Please pass correct browser : "+browserName);
			break;
		}
		
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url").trim());
		
		return getDriver();
	}

	/**
	 * This method is used to run the execution in remote web driver
	 * @param browser
	 * @param browserVersion
	 */
	private void init_remoteDriver(String browser, String browserVersion) {
		System.out.println("Executing in a remote driver");

		if (browser.equalsIgnoreCase("chrome")) {
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability("browserName", "chrome");
			cap.setCapability("browser_version", browserVersion);
			cap.setCapability("enableVNC", true);
			cap.setCapability(ChromeOptions.CAPABILITY, optionManager.getChromeOptions());
			try {
				tldriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), cap));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else if (browser.equalsIgnoreCase("firefox")) {
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability("browserName", "firefox");
			cap.setCapability("browser_version", browserVersion);
			cap.setCapability("enableVNC", true);
			cap.setCapability(FirefoxOptions.FIREFOX_OPTIONS, optionManager.getFirefoxOptions());
			try {
				tldriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), cap));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

	}

	public static WebDriver getDriver() {
		return tldriver.get();
	}
	
	/**
	 * This method is used to read key value pair from properties file
	 * @return {@link Properties}
	 */
	public Properties init_prop() {
		FileInputStream fis = null;
		prop = new Properties();
		//Integrating multiple environment configuration files.
		String env = System.getProperty("env");
		if(env == null) {
			System.out.println("Running on environment : "+"Prod");
			try {
				fis = new FileInputStream("./src/test/resources/config/config.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("Running on environment : "+env);
			try {
				switch (env.toLowerCase()) {
				case "qa":
					fis = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "uat":
					fis = new FileInputStream("./src/test/resources/config/uat.config.properties");
					break;
				default:
					System.out.println("Pass correct environment : "+env.toLowerCase());
					break;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}			
		}
		try {
			prop.load(fis);
		} catch (IOException e) {	
			e.printStackTrace();
		}
		return prop;
	}
	
	/**
	 * This method is to take screenshot
	 * @return
	 */
	public String getScreenshot() {
		File src = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		//Creating a path within our project to save the file
		String path = System.getProperty("user.dir")+"/screenshots/"+System.currentTimeMillis()+".png";
		//As movement will happen file to file, creating a new file at proj path
		File destination = new File(path);
		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
}
