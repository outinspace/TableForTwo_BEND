package space.outin.reservation_application.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestAppUser {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeClass
  public static void setupClass() {
    WebDriverManager.chromedriver().setup();
  }

  @Before
  public void setUp() throws Exception {
    //System.setProperty("webdriver.chrome.driver", "");
    driver = new ChromeDriver();
    baseUrl = "https://www.katalon.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testAppUser() throws Exception {
    driver.get("https://reservations.outin.space/reservations/#/");
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Reservations Application'])[1]/following::i[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[1]/following::input[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[1]/following::div[18]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Create New Account'])[1]/following::input[1]")).clear();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Create New Account'])[1]/following::input[1]")).sendKeys("user@test.com");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Create New Account'])[1]/following::input[2]")).clear();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Create New Account'])[1]/following::input[2]")).sendKeys("testpass");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Create New Account'])[1]/following::input[3]")).clear();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Create New Account'])[1]/following::input[3]")).sendKeys("test");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Create New Account'])[1]/following::input[4]")).clear();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Create New Account'])[1]/following::input[4]")).sendKeys("one");
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Back'])[1]/following::div[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Trending Restaurants'])[1]/following::span[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Close'])[1]/following::div[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='McDonalds'])[1]/following::i[2]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='AM'])[1]/following::div[3]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='McDonalds'])[1]/following::input[2]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='McDonalds'])[1]/following::input[2]")).clear();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='McDonalds'])[1]/following::input[2]")).sendKeys("03:33");
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='McDonalds'])[1]/following::input[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='S'])[2]/following::div[25]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Cancel'])[1]/following::div[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Current Reservations'])[1]/following::div[8]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Edit Reservation'])[2]/following::div[2]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Cancel'])[2]/following::div[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.id("app-toolbar-title")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Reservations Application'])[1]/following::div[4]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Reservations Application'])[1]/following::i[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='PM'])[2]/following::div[4]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Delete Account'])[1]/following::input[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Delete Account'])[1]/following::input[1]")).clear();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Delete Account'])[1]/following::input[1]")).sendKeys("testpass");
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Delete Account'])[1]/following::div[10]")).click();
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
