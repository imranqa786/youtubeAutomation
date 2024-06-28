package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

     public static void click(WebElement elem){
        if(elem.isDisplayed()){
            elem.click();
        }else{
            System.out.println("Element is not displayed");
        }
     }

     public static  void sendKeys(WebElement element,String keysToSend){
        if(element.isDisplayed()){
            element.clear();
            element.sendKeys(keysToSend);
        }else{
            System.out.println("Webelemen you are searching is not displayed");
        }
     }
    
}
