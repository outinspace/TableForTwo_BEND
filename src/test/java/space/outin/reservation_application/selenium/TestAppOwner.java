package space.outin.reservation_application.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestAppOwner {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  
  @BeforeClass
  public static void setupClass() {
    ChromeDriverManager.getInstance(DriverManagerType.CHROME).setup();
  }

  @Before
  public void setUp() throws Exception {
    //System.setProperty("webdriver.chrome.driver", "");
    driver = new ChromeDriver();
    baseUrl = "https://www.katalon.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testAppOwner() throws Exception {
    driver.get("https://reservations.outin.space/reservations/#/");
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Two'])[1]/following::button[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[1]/following::input[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[1]/following::input[1]")).clear();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[1]/following::input[1]")).sendKeys("owner@test.com");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[1]/following::input[2]")).clear();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[1]/following::input[2]")).sendKeys("testpass");
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[1]/following::input[2]")).sendKeys(Keys.ENTER);
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Two'])[1]/following::div[2]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Logout'])[1]/preceding::div[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Publish Restaurant'])[1]/following::button[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Log In'])[1]/following::b[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Trending Restaurants'])[1]/following::input[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Trending Restaurants'])[1]/following::input[1]")).clear();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Trending Restaurants'])[1]/following::input[1]")).sendKeys("test");
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Trending Restaurants'])[1]/following::input[1]")).sendKeys(Keys.ENTER);
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Trending Restaurants'])[1]/following::i[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Two'])[1]/following::button[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Account'])[1]/following::div[2]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Two'])[1]/following::i[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[1]/following::input[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[1]/following::input[1]")).clear();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[1]/following::input[1]")).sendKeys("user@test.com");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[1]/following::input[2]")).clear();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[1]/following::input[2]")).sendKeys("testpass");
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[1]/following::input[2]")).sendKeys(Keys.ENTER);
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Trending Restaurants'])[1]/following::input[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Trending Restaurants'])[1]/following::input[1]")).clear();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Trending Restaurants'])[1]/following::input[1]")).sendKeys("test");
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Trending Restaurants'])[1]/following::input[1]")).sendKeys(Keys.ENTER);
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Trending Restaurants'])[1]/following::span[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Close'])[1]/following::div[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Test Restaurant'])[1]/following::input[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='S'])[2]/following::div[27]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Cancel'])[1]/following::div[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Two'])[1]/following::div[2]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Account'])[1]/following::div[2]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Two'])[1]/following::button[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[1]/following::input[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[1]/following::input[1]")).clear();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[1]/following::input[1]")).sendKeys("owner@test.com");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[1]/following::input[2]")).clear();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[1]/following::input[2]")).sendKeys("testpass");
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[1]/following::input[2]")).sendKeys(Keys.ENTER);
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Two'])[1]/following::div[4]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='one, test'])[1]/following::div[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='View Reservation'])[1]/following::div[51]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='View'])[1]/following::div[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Cancel'])[1]/following::div[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Two'])[1]/following::div[2]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='All'])[3]/following::div[4]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Unpublish Restaurant'])[1]/following::button[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Table'])[1]/following::i[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Trending Restaurants'])[1]/following::input[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Trending Restaurants'])[1]/following::input[1]")).clear();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Trending Restaurants'])[1]/following::input[1]")).sendKeys("test");
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Trending Restaurants'])[1]/following::input[1]")).sendKeys(Keys.ENTER);
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Two'])[1]/following::div[2]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Account'])[1]/following::div[2]")).click();
    Thread.sleep(2000);
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
