package Demo;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class simple {

	public static void main(String[] args) throws InterruptedException, MalformedURLException, IOException {
		//initialization 
		//setting property 
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Microsoft\\eclipse-workspace\\Nykaa\\Driver\\Chrome\\chromedriver.exe");
		//creating object with desired capability
		ChromeOptions ch = new ChromeOptions();
		ch.addArguments("--start-maximized");
		WebDriver driver = new ChromeDriver(ch);
		//open url 
		driver.navigate().to("https://www.nykaa.com/");
		//maximize 
		//driver.manage().window().maximize();
		//suggestion box 
		WebElement sug = driver.findElement(By.xpath("//input[@name='search-suggestions-nykaa']"));
		sug.sendKeys("M");
		sug.click();
		
		//wait(explicit)
		WebDriverWait w = new WebDriverWait(driver, 5);
		w.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='css-1nsqxbe e80op0e0']")));
//		w.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//div[@class='css-1nsqxbe e80op0e0']/div"))));
//		Thread.sleep(2000);
		List<WebElement> li = driver.findElements(By.xpath("//div[@class='css-1nsqxbe e80op0e0']/div"));
		//System.out.print(li);
		for (WebElement s : li) {
			//System.out.println(s.getText());
			if(s.getText().equalsIgnoreCase("M.A.C")) {
				s.click();
				break;
			}
		}
		
		//action class 
		Actions ac = new Actions(driver);
		ac.click(driver.findElement(By.xpath("//a[@title='logo']"))).build().perform();
		//mousehover
		ac.pause(2000).moveToElement(driver.findElement(By.xpath("//a[text()='brands']"))).perform();
		ac.pause(2000).moveToElement(driver.findElement(By.xpath("//a[text()='M']"))).perform();
		ac.pause(2000).moveToElement(driver.findElement(By.xpath("//a[text()='MAKE UP FOR EVER']"))).click().perform();
		
		
		//how to find iframe in chrome 
		
		//screenshot 
		//one element 
		WebElement app = driver.findElement(By.xpath("//div[@class='slick-track']"));
		File sc = app.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc,new File("C:\\Users\\Microsoft\\eclipse-workspace\\Nykaa\\Screenshot\\one.jpg")); //import common 
		
		//visible
		File sc1= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sc1, new File("C:\\Users\\Microsoft\\eclipse-workspace\\Nykaa\\Screenshot\\Two.jpg"));
		
		Screenshot sc2= new AShot().shootingStrategy(ShootingStrategies.viewportPasting(2000)).takeScreenshot(driver);
		ImageIO.write(sc2.getImage(), "PNG", new File("C:\\Users\\Microsoft\\eclipse-workspace\\Nykaa\\Screenshot\\Full.jpg"));
		
		
		
		
		//link handeling 
		HttpURLConnection h1;
		List<WebElement> li1= driver.findElements(By.tagName("a"));
		for (WebElement li2 : li1) {
			String url = li2.getAttribute("href");
			if( (url==null) || url.isEmpty()) {
				System.out.println("Empty link");
				continue; 
			}
			h1 = (HttpURLConnection)new URL(url).openConnection();
			h1.setRequestMethod("HEAD");
			h1.connect();
			int responsecode = h1.getResponseCode();
			if(responsecode>=400) {
				System.out.println("This is broken link"+url);
			}
		}
		
		
	
		
		
				

	}

}
