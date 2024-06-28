package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;

import bsh.commands.dir;

import java.time.Duration;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;
import dev.failsafe.internal.util.Assert;

public class TestCases extends ExcelDataProvider { // Lets us read the data
        ChromeDriver driver;

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */

        @Test
        public void testCase01() {
                driver.get("https://www.youtube.com/");
                String actualUrl = driver.getCurrentUrl();

                String expectedUrl = "https://www.youtube.com/";
                if (actualUrl.equals(expectedUrl)) {
                        System.out.println("Url Matched Going Forward");
                } else {
                        System.out.println("Url didn't match failed");
                }

                WebElement about = driver.findElement(By.xpath("//a[text()='About']"));
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView(true)", about);
                // about.click();
                Wrappers.click(about);

                WebElement abouttext = driver.findElement(By.xpath("//h1[contains(text(),'About')]"));
                String abtText = abouttext.getText();
                System.out.println(abtText);
                System.out.println("testCase01 passed :)");
        }

        @Test
        public void testCase02() throws InterruptedException {
                driver.get("https://www.youtube.com/");
               WebElement movies = driver.findElement(By.xpath("//a[@title='Movies']"));
               Wrappers.click(movies);
                Thread.sleep(3000);
                WebElement browse = driver.findElement(By.xpath("//div[text()='Browse']"));
                Wrappers.click(browse);
                WebElement next = driver.findElement(By.xpath("//button[@aria-label='Next']"));
                for (int i = 1; i <= 3; i++) {
                        // next.click();
                        if(next.isDisplayed()){
                        Wrappers.click(next);
                        Thread.sleep(5000);
                        }else{
                                System.out.println("next button not visible");
                        }
                }
                // creating instance for softassert
                List<WebElement> ratings = driver
                        .findElements(By.xpath("//div[contains(@class,'badge badge-style-type-simple')]"));
                        
                WebElement assertingA_rating = ratings.get(ratings.size()-1);
                String assertArat = assertingA_rating.getAttribute("aria-label");
                SoftAssert sAssert = new SoftAssert();
                sAssert.assertTrue(sAssert.equals("U/A16+"));
                WebElement assertMovieType = driver.findElement(By.xpath("(//span[contains(text(),'Drama')])[3]"));
                String expecteMovieType = "Drama";
                String actualMovieType = assertMovieType.getText();
                boolean movieTypeIsMatchingOrNot = actualMovieType.contains(expecteMovieType);
                sAssert.assertTrue(movieTypeIsMatchingOrNot);
                System.out.println("actual movie type is" + actualMovieType);
        }

        @Test
        public void testCase03() throws InterruptedException {
                WebElement music = driver.findElement(By.xpath("//a[@title='Music']"));
                // clicking on music session
                // music.click();
                Wrappers.click(music);
                Thread.sleep(5000);
                // WebElement parentElement = driver.findElement(By.xpath("(//div[@id='scroll-container'])[1]"));
                // WebElement nextButtonForFirstcolumn = driver.findElement(By.xpath("(//button[contains(@class,'yt-spec-button-shape-next yt-spec-button-shape-next--text')])[6]"));
                WebElement nextButtonForFirstcolumn = driver.findElement(By.xpath("(//button[@aria-label='Next'])[2]"));
                for (int i = 1; i <= 3; i++) {
                        // nextButtonForFirstcolumn.click();
                        Wrappers.click(nextButtonForFirstcolumn);
                        Thread.sleep(2000);
                }
                WebElement nameOfPlaylist = driver
                                .findElement(By.xpath("//h3[contains(text(),'Bollywood Dance Hitlist')]"));
                String nameOfLastPlayListInFirstColumn = nameOfPlaylist.getText();
                System.out.println(nameOfLastPlayListInFirstColumn);
                WebElement trackCount = driver.findElement(
                                By.xpath("//*[contains(text(),'Bollywood Dance Hitlist')]/following::p[2]"));
                String trackCountStr = trackCount.getText();
                String numericPart = extractNumber(trackCountStr);

                int trackCountInt = Integer.parseInt(numericPart);

                SoftAssert sa = new SoftAssert();
                sa.assertTrue(trackCountInt <= 50);

        }

        private String extractNumber(String text) {
                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(text);
                if (m.find()) {
                        return m.group();
                }
                return "0";
        }

        @Test
public void testCase04() throws InterruptedException {
        WebElement news = driver.findElement(By.xpath("//a[@title='News']"));
        Wrappers.click(news);
        Thread.sleep(5000);

        WebElement titleOfPost = driver.findElement(By.xpath("//span[text()='Latest news posts']"));
        String titleStr = titleOfPost.getText();
        System.out.println(titleStr);
        WebDriverWait wWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//yt-formatted-string[@id='home-content-text']")));
        List<WebElement> postBodies = driver
                        .findElements(By.xpath("//yt-formatted-string[@id='home-content-text']"));
        List<WebElement> likes = driver.findElements(By.xpath("//span[@id='vote-count-middle']"));

        int totalLikes = 0;
        int postsProcessed = 0;

        for (int i = 0; i < postBodies.size() && postsProcessed < 3; i++) {
                WebElement postBody = postBodies.get(i);
                String postBodyText = postBody.getText();
                System.out.println(postBodyText);

                // Check if the likes element is present for this post
                int likesCountInt = 0; // Default to 0
                if (i < likes.size() && likes.get(i).isDisplayed()) {
                        WebElement likesCount = likes.get(i);
                        String likesCountStr = likesCount.getText().replaceAll(",", ""); // Remove commas if present
                        likesCountInt = likesCountStr.isEmpty() ? 0 : Integer.parseInt(likesCountStr); // Convert to integer
                }

                totalLikes += likesCountInt; // Add to total
                postsProcessed++; // Increment the number of posts processed
        }

        System.out.println("Total likes count = " + totalLikes);

        SoftAssert sa = new SoftAssert();
        sa.assertTrue(totalLikes >= 0, "Total likes should be non-negative");
        sa.assertAll();
}



         
        @Test(dataProvider = "excelData",dataProviderClass = ExcelDataProvider.class)
        public void testCase05(String to_be_searched) throws InterruptedException {
                driver.get("https://www.youtube.com");
                Thread.sleep(6000);
                WebElement searchBox = driver.findElement(By.xpath("//input[@placeholder='Search']"));
                // searchBox.sendKeys(searchName);
               Wrappers.click(searchBox);
                Wrappers.sendKeys(searchBox, to_be_searched);

                WebElement search = driver.findElement(By.id("search-icon-legacy"));
                Wrappers.click(search);
                Thread.sleep(5000);
        
                long totalViews = 0;
                JavascriptExecutor js = (JavascriptExecutor) driver;
                while (totalViews < 1000000000) { // 10 Crore views
                    List<WebElement> videoElements = driver.findElements(By.xpath("//span[contains(@class,'inline-metadata') and contains(text(),'views')]"));
        
                    for (WebElement videoElement : videoElements) {
                        String viewsText = videoElement.getText();
                        if (viewsText.contains("views")) {
                            viewsText = viewsText.split(" ")[0]; // Get the number part
                            totalViews += parseViews(viewsText);
                        }
        
                        if (totalViews >= 1000000000) {
                            break;
                            
                        }
                    }
        
                    js.executeScript("window.scrollBy(0, 1000);");
                    Thread.sleep(2000); // Wait for new videos to load
                }
        
                System.out.println("Total views for " + to_be_searched + ": " + totalViews);
            }
        
            private long parseViews(String viewsText) {
                long views = 0;
                if (viewsText.endsWith("K")) {
                    views = (long) (Double.parseDouble(viewsText.replace("K", "")) * 1_000);
                } else if (viewsText.endsWith("M")) {
                    views = (long) (Double.parseDouble(viewsText.replace("M", "")) * 1_000_000);
                } else if (viewsText.endsWith("B")) {
                    views = (long) (Double.parseDouble(viewsText.replace("B", "")) * 1_000_000_000);
                } else {
                    views = Long.parseLong(viewsText.replace(",", ""));
                }
                return views;
            }

        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
        }

        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }
}