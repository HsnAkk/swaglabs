package TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import pageobjects.LandingPage;

public class BaseTest {

	public WebDriver driver;
	public LandingPage landingPage;

	public WebDriver initializeDriver() throws IOException {

		// properties class to read global variables
		Properties prop = new Properties();
		// load the GlobalData.properties file
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "//src//main//java//resources//GlobalData.properties");
		prop.load(fis);

		// System level variables from MAVEN, so that in TERMINAL you can send as
		// parameter dynamically
		String browserName = System.getProperty("browser") != null ? System.getProperty("browser")
				: prop.getProperty("browser");

		if (browserName.contains("chrome")) {

			// in order to run chrome browser in headless mode, we need set chrome options
			ChromeOptions options = new ChromeOptions();

			// ChromeDriver
			WebDriverManager.chromedriver().setup();

			if (browserName.contains("headless")) {
				// mvn test -PRegression -Dbrowser=chromeheadless
				options.addArguments("headless");
			}

			// System.setProperty("webdriver.chrome.driver",
			// "/Users/albatros/Selenium_Training/drivers/chromedriver");
			driver = new ChromeDriver(options);

			// to be able to maximize window in headless mode
			driver.manage().window().setSize(new Dimension(1440, 990));

		} else if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "/Users/albatros/Selenium_Training/drivers/geckodriver");
			driver = new FirefoxDriver();

		} else if (browserName.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", "/Users/albatros/Selenium_Training/drivers/msedgedriver");
			driver = new EdgeDriver();
		}

		// implicitly wait
		// driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.manage().window().maximize();

		return driver;
	}
	
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {

		// read JSON to String
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

		// convert String to HashMap
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});

		return data;
	}
	
	@BeforeMethod(alwaysRun = true)
	public LandingPage lunchApplication() throws IOException {
		driver = initializeDriver();

		// LANDING PAGE
		landingPage = new LandingPage(driver);
		landingPage.goTo();

		return landingPage;
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.close();
	}

}
