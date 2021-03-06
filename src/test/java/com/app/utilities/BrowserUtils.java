package com.app.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrowserUtils {
	
	public static void scrollDown(WebElement element) {
		  element.click();
		  JavascriptExecutor jse = (JavascriptExecutor)Driver.getDriver();
		  jse.executeScript("window.scrollBy(0,1600);");
	}

	public static List<String> convertToString(List<WebElement> el) {
		List<String> strs = new ArrayList<>();
		for (WebElement e : el) {
			if (!e.getText().isEmpty()) {
				strs.add(e.getText());
			}
		}
		return strs;
	}

	public static void hover(WebElement el) {
		Actions action = new Actions(Driver.getDriver());
		action.moveToElement(el).perform();
		;
	}

	public static WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
		WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeToWaitInSec);
		return wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public static void waitForTextApear(WebElement element,String text, int timeToWaitInSec) {
		WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeToWaitInSec);
	    wait.until(ExpectedConditions.textToBePresentInElementValue(element, text));
	}

	public static WebElement waitForVisibility(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeout);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public static WebElement waitForClickablility(WebElement element, int timeout) {
		WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeout);
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public static WebElement waitForClickablility(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeout);
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public static WebElement fluentWait(final WebElement webElement, int timeinsec) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getDriver())
				.withTimeout(timeinsec, TimeUnit.SECONDS).pollingEvery(timeinsec, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);
		WebElement element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return webElement;
			}
		});
		return element;
	}

	public static void waitForPageToLoad(long timeOutInSeconds) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		try {
			System.out.println("Waiting for page to load...");
			WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeOutInSeconds);
			wait.until(expectation);
		} catch (Throwable error) {
			System.out.println(
					"Timeout waiting for Page Load Request to complete after " + timeOutInSeconds + " seconds");
		}
	}

	public static void waitFor(int sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void switchToWindow(String targetTitle) {
		String origin = Driver.getDriver().getWindowHandle();
		for (String handle : Driver.getDriver().getWindowHandles()) {
			Driver.getDriver().switchTo().window(handle);
			if (Driver.getDriver().getTitle().equals(targetTitle)) {
				return;
			}
		}
		Driver.getDriver().switchTo().window(origin);
	}

	public static List<String> getElementsText(By locator) {
		List<WebElement> list = Driver.getDriver().findElements(locator);
		List<String> elemTexts = new ArrayList<>();
		for (WebElement element : list) {
			if (!element.getText().isEmpty()) {
				elemTexts.add(element.getText());
			}
		}
		return elemTexts;
	}

	// public static void getScreenShot(String fileName) throws IOException {
	// File outputFile = ((TakesScreenshot)
	// driver).getScreenshotAs(OutputType.FILE);
	// FileUtils.copyFile(outputFile, new File(System.getProperty("user.dir")
	// + "//src//test//java//com//companyname//projectname//screenshots//" +
	// fileName + ".jpg"));
	// }
}
