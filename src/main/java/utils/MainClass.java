package utils;


import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

public class MainClass extends WebBrowser {
    private static SoftAssert softAssert = new SoftAssert();

    private static WebElement getEl(By by) {
        WebElement element = null;
        System.out.println("[INFO] Trying to get element selector: '" + by.toString() + "'");
        WebDriverWait wait = new WebDriverWait(Driver(), 30);
        element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        System.out.println("[INFO] Gotten element selector: '" + by.toString() + "'");
        return element;
    }

    public static String getTitle() {
        String title = Driver().getTitle();
        return title;
    }

    public static String getCurrentUrl() {
        String currAddress = Driver().getCurrentUrl();
        String title = Driver().getTitle();
        if (title.contains("is not available") || title.contains("Problem loading page")) {
            myLogger().log(LogStatus.FAIL, title + myLogger().addScreenCapture(Screenshot.take()));
        }
        return currAddress;
    }

    public static WebElement getElement(By by) {
        WebElement element = null;
        try {
            element = getEl(by);
            System.out.println("[INFO] Gotten element selector: '" + by.toString() + "'");
        } catch (Exception e) {
            myLogger().log(LogStatus.INFO, "Trying to get element Selector: '" + by.toString() + "'");
            System.out.println("[INFO] Trying to get element Selector: '" + by.toString() + "'");
            myLogger().log(LogStatus.FAIL,
                    "Cannot get element " + myLogger().addScreenCapture(Screenshot.take()) + e.getCause());
            System.out.println("[FAIL] Cannot get element Selector: '" + by.toString() + "'");
            e.printStackTrace();
        }
        return element;
    }

    public static void clickOn(By by, String elementName) {
        WebElement element = null;
        try {
            element = getEl(by);
            element.click();
            myLogger().log(LogStatus.PASS, "Clicked on '" + elementName + "'");
            System.out.println("[INFO] Clicked on '" + elementName + "' Selector: '" + by.toString() + "'");
        } catch (Exception e) {
            myLogger().log(LogStatus.INFO, "Trying to click on '" + elementName + "' ...");
            System.out.println("[INFO] Trying to click on '" + elementName + "' Selector: '" + by.toString() + "'");
            myLogger().log(LogStatus.FAIL, "Cannot click on element '" + elementName + "' "
                    + myLogger().addScreenCapture(Screenshot.take()) + e.getCause());
            System.out
                    .println("[FAIL] Cannot click on element '" + elementName + "' Selector: '" + by.toString() + "'");
            e.printStackTrace();
            Assert.fail("Failed", e.fillInStackTrace());
        }
    }


    public static void enterText(By by, String text) {
        WebElement element = null;
        try {
            element = getEl(by);
            element.clear();
            element.sendKeys(text);
            myLogger().log(LogStatus.PASS, "Entered text '" + text + "'");
            System.out.println("[INFO] Entered text '" + text + "' to element Selector: '" + by.toString() + "'");
        } catch (Exception e) {
            myLogger().log(LogStatus.INFO, "Trying to enter text '" + text + "'...");
            System.out
                    .println("[INFO] Trying to enter text '" + text + "' to element Selector: '" + by.toString() + "'");
            myLogger().log(LogStatus.FAIL,
                    "Cannot enter text '" + text + "'" + myLogger().addScreenCapture(Screenshot.take()) + e.getCause());
            System.out.println("[FAIL] Cannot enter text '" + text + "' to element Selector: '" + by.toString() + "'");
            e.printStackTrace();
            Assert.fail("Failed", e.fillInStackTrace());
        }
    }

    public static void switchToFrame(String frameId) {
        try {
            System.out.println("[INFO] Trying to switch to frame '" + frameId + "'");
            Driver().switchTo().frame(frameId);
            System.out.println("[INFO] Switched to frame '" + frameId + "'");
        } catch (Exception e) {
            myLogger().log(LogStatus.INFO, "Trying to switch to frame '" + frameId + "'");
            System.out.println("[INFO] Trying to switch to frame '" + frameId + "'");
            myLogger().log(LogStatus.FAIL, "Cannot find frame: '" + frameId + "'");
            System.out.println("[FAILED] Cannot find frame: '" + frameId + "'");
            e.printStackTrace();
            Assert.fail("Failed", e.fillInStackTrace());
        }
    }

    public static void sleepFor(int msec) {
        try {
            System.out.println("[INFO] Sleeping for " + msec + " milliseconds");
            Thread.sleep(msec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static WebDriver switchToTab(int i) {
        WebDriver tab = null;
        try {
            System.out.println("[INFO] Trying to switch to tab " + i);
            tab = Driver().switchTo().window(getTabs().get(i));
            myLogger().log(LogStatus.PASS, "Switched to tab '" + tab.getTitle() + "'");
            System.out.println("[INFO] Switched to tab '" + tab.getTitle() + "'");
        } catch (Exception e) {
            myLogger().log(LogStatus.INFO, "Trying to switch to tab " + i);
            System.out.println("[INFO] Trying to switch to tab " + i);
            myLogger().log(LogStatus.FAIL, "Cannot switch to tab " + i);
            System.out.println("[FAIL] Cannot switch to tab " + i);
            e.printStackTrace();
            Assert.fail("Failed", e.fillInStackTrace());
        }
        return tab;
    }

    private static List<String> getTabs() {
        List<String> windows = new ArrayList<String>(Driver().getWindowHandles());
        return windows;
    }

    public static void closeTab(int i) {
        switchToTab(i).close();
        myLogger().log(LogStatus.PASS, "Tab closed");
        System.out.println("[INFO] Tab closed");
    }

    public static String getElementAtt(By by, String attName) {
        String att = null;
        try {
            att = getEl(by).getAttribute(attName);
            System.out.println("[INFO] Gotten attribute '" + attName + ". Value: '" + att
                    + "' from elemnent (selector: '" + by.toString() + "')");
        } catch (Exception e) {
            myLogger().log(LogStatus.INFO, "Trying to get attribute '" + attName + "'");
            System.out.println(
                    "[INFO] Trying to get attribute '" + attName + "' from elemnent Selector: '" + by.toString() + "'");
            myLogger().log(LogStatus.FAIL, "Cannot get attribute '" + attName + "' "
                    + myLogger().addScreenCapture(Screenshot.take()) + e.getCause());
            System.out.println(
                    "[FAIL] Cannot get attribute '" + attName + "' from elemnent Selector: '" + by.toString() + "'");
            e.printStackTrace();
            Assert.fail("Failed", e.fillInStackTrace());
        }
        return att;
    }

    public static String getElementText(By by) {
        String text = null;
        try {
            text = getEl(by).getText();
            System.out.println("[INFO] Gotten text: '" + text + "' from elemnent Selector: '" + by.toString() + "')");
        } catch (Exception e) {
            myLogger().log(LogStatus.INFO, "Trying to get text...");
            System.out.println("Trying to get text '" + text + "' from elemnent Selector: '" + by.toString() + "'");
            myLogger().log(LogStatus.FAIL,
                    "Cannot get text" + myLogger().addScreenCapture(Screenshot.take()) + e.getCause());
            System.out
                    .println("[FAILED] Cannot get text '" + text + "' from elemnent Selector: '" + by.toString() + "'");
            e.printStackTrace();
            Assert.fail("Failed", e.fillInStackTrace());
        }
        return text;
    }

    public static void switchToDefaultFrame() {
        Driver().switchTo().defaultContent();
        System.out.println("[INFO] Switched to default frame");
    }

    public static List<WebElement> getElements(By by) {
        return Driver().findElements(by);
    }

    public static void assertEquals(String beforeMess, Object actual, Object expected) {
        myLogger().log(LogStatus.INFO, beforeMess);
        System.out.println("[INFO] Assertion method is invoked. Details:" + beforeMess);
        try {
            Assert.assertEquals(actual, expected);
            myLogger().log(LogStatus.PASS, "Objects match");
            System.out.println("[INFO] Objects match");
        } catch (AssertionError e) {
            myLogger().log(LogStatus.FAIL, "Expected: '" + expected + "' Actual: '" + actual + "'"
                    + myLogger().addScreenCapture(Screenshot.take()));
            System.out.println("[FAILED] Expected: '" + expected + "' Actual: '" + actual + "'");
            Assert.fail("Failed", e.fillInStackTrace());
        }
    }

    public static void assertTrue(String verificationMessage, boolean actual) {
        myLogger().log(LogStatus.INFO, verificationMessage);
        System.out.println("[INFO] Assertion method is invoked. Details: " + verificationMessage);
        try {
            Assert.assertTrue(actual);
            myLogger().log(LogStatus.PASS, "TRUE");
            System.out.println("[INFO] Assertion status: TRUE");
        } catch (AssertionError e) {
            myLogger().log(LogStatus.FAIL, "FALSE" + myLogger().addScreenCapture(Screenshot.take()) + e.getMessage());
            System.out.println("[FAILED] Assertion status: FALSE");
            e.printStackTrace();
            Assert.fail("Failed", e.fillInStackTrace());
        }
    }

    public static boolean isElementDisplayed(By by) {
        WebElement element = null;
        boolean b = false;
        try {
            element = getEl(by);
            if (element.isDisplayed()) {
                b = true;
            } else {
                b = false;
            }
        } catch (Exception e) {
        }
        return b;
    }

    public static void selectFromDropdownText(By dropDownIdent, String text) {
        try {
            Select oSelection = new Select(getEl(dropDownIdent));
            oSelection.selectByVisibleText(text);
            myLogger().log(LogStatus.PASS, "Selected '" + text + "'");
            System.out.println(
                    "[INFO] Selected '" + text + "' from dropdown Selector: '" + dropDownIdent.toString() + "'");
        } catch (Exception e) {
            myLogger().log(LogStatus.INFO, "Trying to select '" + text + "' from dropdown");
            System.out.println("[INFO] Trying to select '" + text + "' from dropdown Selector: '"
                    + dropDownIdent.toString() + "'");
            myLogger().log(LogStatus.FAIL, "Cannot select '" + text + "' from dropdown"
                    + myLogger().addScreenCapture(Screenshot.take()) + e.getCause());
            System.out.println(
                    "[FAILED] Cannot select '" + text + "' from dropdown Selector: '" + dropDownIdent.toString() + "'");
            e.printStackTrace();
            Assert.fail("Failed", e.fillInStackTrace());
        }
    }

    public static void acceptAlert() {
        WebDriverWait wait = new WebDriverWait(Driver(), 5);
        wait.until(ExpectedConditions.alertIsPresent());
        System.out.println("[INFO] Waiting for alert...");
        Alert alert = Driver().switchTo().alert();
        alert.accept();
        System.out.println("[INFO] Alert accepted");
    }

    public static void verifyTrue(String message, boolean verification) {
        myLogger().log(LogStatus.INFO, message);
        if (verification) {
            myLogger().log(LogStatus.PASS, "TRUE");
        } else {
            myLogger().log(LogStatus.FAIL, "FALSE" + myLogger().addScreenCapture(Screenshot.take()));
            softAssert.fail();
        }
    }

    public static void verifyAll() {
        softAssert.assertAll();
    }

    public void getPage(String pageURL) {
        try {
            Driver().get(pageURL);
            myLogger().log(LogStatus.PASS, "Redirected to '" + pageURL + "'");
            System.out.println("[INFO] Redirected to '" + pageURL + "'");
        } catch (Exception e) {
            myLogger().log(LogStatus.INFO, "Redirecting to '" + pageURL + "' ...");
            System.out.println("[INFO] Redirecting to '" + pageURL + "' ...");
            myLogger().log(LogStatus.FAIL, "Page is not available" + myLogger().addScreenCapture(Screenshot.take()));
            System.out.println("[FAIL] Page is not available");
            e.printStackTrace();
            Assert.fail("Failed", e.fillInStackTrace());
        }
    }

    public void selectFromDropdownValue(By dropDownIdent, String value) {
        try {
            Select oSelection = new Select(getEl(dropDownIdent));
            oSelection.selectByValue(value);
            myLogger().log(LogStatus.PASS, "Selected '" + value + "' from dropdown");
            System.out.println(
                    "[INFO] Selected '" + value + "' from dropdown Selector: '" + dropDownIdent.toString() + "'");
        } catch (Exception e) {
            myLogger().log(LogStatus.INFO,
                    "Trying to select '" + value + "' from dropdown Selector: '" + dropDownIdent.toString() + "'");
            System.out.println("[FAILED] Trying to select '" + value + "' from dropdown Selector: '"
                    + dropDownIdent.toString() + "'");
            myLogger().log(LogStatus.PASS, "Cannot select '" + value + "' from dropdown Selector: '"
                    + dropDownIdent.toString() + "'" + myLogger().addScreenCapture(Screenshot.take()) + e.getCause());
            System.out.println("[FAILED] Cannot select '" + value + "' from dropdown Selector: '"
                    + dropDownIdent.toString() + "'");
            e.printStackTrace();
            Assert.fail("Failed", e.fillInStackTrace());
        }
    }

}

