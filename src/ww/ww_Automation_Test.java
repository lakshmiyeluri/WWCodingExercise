package ww;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.junit.Assert.*;

public class ww_Automation_Test {

	public static WebDriver driver;

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
		openApp();
		assertTitle();
		locationSearch();
		locationTiming();
		printMeetings("SUN");
		closeApp();
	}

	@Test
	public static void openApp() {
		// To Navigate to https://www.weightwatchers.com/us/
		driver = new FirefoxDriver();
		driver.get("https://www.weightwatchers.com/us/");
	}

	@Test
	public static void assertTitle() throws InterruptedException {
		// Assert loaded page title matches “WW (Weight Watchers): Weight Loss &
		// Wellness Help | WW USA”
		String ActualTitle = driver.getTitle();
		String ExpectedTitle = "WW (Weight Watchers): Weight Loss & Wellness Help | WW USA";
		Assert.assertEquals(ExpectedTitle, ActualTitle);

		// Click on find the workshop
		driver.findElement(By.className("MenuItem_menu-item__icon-wrapper__3_QQx")).click();
		Thread.sleep(5000);

		// Assert loaded page title contains “Find WW Studios & Meetings Near You | WW
		// USA”
		String actualString = driver.getTitle();
		System.out.println(actualString);
		String specificText = "Find WW Studios & Meetings Near You | WW USA";
		assertTrue(actualString.contains(specificText));
	}

	@Test
	public static void locationSearch() {
		// Search for meetings for zip code: 10011
		WebElement locationSearch = driver.findElement(By.xpath("//*[@id='location-search']"));
		WebDriverWait wait = new WebDriverWait(driver, 2000);
		wait.until(ExpectedConditions.elementToBeClickable(locationSearch));
		driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
		locationSearch.sendKeys("10011");
		driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id='location-search-cta']")).sendKeys(Keys.ENTER);

		// Print the title of the first result and the distance
		List<WebElement> StudioName = driver.findElements(By.className("linkUnderline-1_h4g"));
		WebElement FirstStudio = StudioName.get(0);
		String StudioTitle = FirstStudio.getText();
		System.out.println("Studio Title is " + StudioTitle);
		List<WebElement> distance = driver.findElements(By.className("distance-OhP63"));
		WebElement firstStudioDistance = distance.get(0);
		System.out.println("Studio distance is " + firstStudioDistance.getText());

		// verify displayed location name/title matches with the name of the first
		// searched result that was clicked.
		StudioName.get(0).click();
		String displayedTitle = driver.findElement(By.className("locationName-1jro_")).getText();
		Assert.assertEquals(StudioTitle, displayedTitle);
	}

	@Test
	public static void locationTiming() {
		// print TODAY’s hours of operation
		String locationHours = driver.findElement(By.className("workshopStatus-3OO_w")).getText();
		System.out.println("location hours are " + locationHours);
	}

	@Test
	public static void printMeetings(String dayOfTheWeek) throws InterruptedException {
		WebElement element = driver.findElement(By.className("scheduleContainerMobile-1RfmF"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(500);

		Map<String, List<String>> output = new HashMap<>();
		List<WebElement> days = driver.findElements(By.xpath("//*[@id='main']//div[@class='day-NhBOb']"));

		for (WebElement day : days) {
			List<WebElement> names = day.findElements(By.xpath("div[@class='meeting-14qIm']/span[2]"));
			List<String> temp = new ArrayList<>();

			for (WebElement we : names) {
				temp.add(we.getText());
			}
			output.put(day.findElement(By.xpath("span")).getText(), temp);

		}

		System.out.println(output.get(dayOfTheWeek));
		Map<String, Long> counts = output.get(dayOfTheWeek).stream()
				.collect(Collectors.groupingBy(e -> e, Collectors.counting()));
		System.out.println(counts);
	}

	public static void closeApp() {
		driver.close();

	}

}
