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

public class TestAppOwner {
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
  public void testAppOwner() throws Exception {
    driver.get("https://reservations.outin.space/reservations/#/");
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Reservations Application'])[1]/following::div[2]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[1]/following::button[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Create New Account'])[1]/following::input[1]")).clear();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Create New Account'])[1]/following::input[1]")).sendKeys("owner@test.com");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Create New Account'])[1]/following::input[2]")).clear();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Create New Account'])[1]/following::input[2]")).sendKeys("testpass");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Create New Account'])[1]/following::input[3]")).clear();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Create New Account'])[1]/following::input[3]")).sendKeys("test");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Create New Account'])[1]/following::input[4]")).clear();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Create New Account'])[1]/following::input[4]")).sendKeys("two");
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Create New Account'])[1]/following::div[34]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Back'])[1]/following::div[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Reservations Application'])[1]/following::div[2]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Logout'])[1]/preceding::div[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Restaurant Profile'])[1]/following::input[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Restaurant Profile'])[1]/following::input[1]")).clear();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Restaurant Profile'])[1]/following::input[1]")).sendKeys("Test Restaurant");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Restaurant Profile'])[1]/following::textarea[1]")).clear();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Restaurant Profile'])[1]/following::textarea[1]")).sendKeys("Restaurant tests");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Restaurant Profile'])[1]/following::input[2]")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Restaurant Profile'])[1]/following::input[2]")).clear();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Restaurant Profile'])[1]/following::input[2]")).sendKeys("123 main st");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Restaurant Profile'])[1]/following::input[3]")).clear();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Restaurant Profile'])[1]/following::input[3]")).sendKeys("9");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='People'])[1]/following::input[1]")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='People'])[1]/following::input[1]")).clear();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='People'])[1]/following::input[1]")).sendKeys("https://flood.io/wp-content/uploads/2015/05/big-logo.png_200181_2015-03-26_14-24-51.jpg");
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='People'])[1]/following::div[15]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Publish Restaurant'])[1]/following::div[2]")).click();
    Thread.sleep(2000);
    driver.findElement(By.id("app-toolbar-title")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Reservations Application'])[1]/following::div[2]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Logout'])[1]/preceding::div[1]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Unpublish Restaurant'])[1]/following::div[2]")).click();
    Thread.sleep(2000);
    driver.findElement(By.id("app-toolbar-title")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Reservations Application'])[1]/following::div[2]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Logout'])[1]/preceding::div[1]")).click();
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
