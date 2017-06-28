package com.seleniumproject;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class TestEbay {
	static WebDriver driver;
	static WebElement searchbox,searchbutton,searchtab,selectproduct,itemcondion,timeleft,productPrice,productName,addCart,shoppingCartHeader,cardProdName,cardProdPrice,totalProdPrice,guestchkoutscreen;
	
	 String itemprice,prodName;

public static void main(String[] args) throws InterruptedException, IOException {
		
		TestEbay testebay=new TestEbay();
		testebay.openapplication();
		testebay.searchbykey();
		testebay.checkListedItemContainsSearchedName(driver, ApplicationConstant.search_key);
		testebay.filterbyscreensize();
		testebay.selectproduct();
		testebay.verifyProductCondition();
		//testebay.timeLeftFormat();
		testebay.verifyPriceFormat();
		testebay.storeProductDetails();
		testebay.addtoCart();
		testebay.verifyShopppingcart();
		testebay.verifyInformationcart();
		testebay.guestCheckout();
		
	

	}



private void openapplication()
{
	System.out.println("Inside");
	driver=new ChromeDriver();
	//driver.manage().window().maximize();
	System.out.println("Inside1");
	System.out.println(ApplicationConstant.search_key);
	driver.get(ApplicationConstant.application_url);
	
}

private void searchbykey() throws InterruptedException {
	System.out.println("Inside searchbykey");
	searchbox=driver.findElement(By.id(ApplicationConstant.searchbox_id));
	searchbox.sendKeys(ApplicationConstant.search_key);
	System.out.println("Inside searchbykey1");
	searchbutton=driver.findElement(By.id(ApplicationConstant.searchbutton_id));
	searchbutton.submit();
	System.out.println("Inside searchbykey2");
	Thread.sleep(1000);

}


private  void checkListedItemContainsSearchedName(
		WebDriver webDriver, String searchKey) throws InterruptedException, IOException  {

	 WebElement searchedItemArea ;
	 searchedItemArea = driver.findElement(By.className("vip"));
     List<WebElement> listItems = searchedItemArea.findElements(By.tagName("a")); 
     
     String[] words=searchKey.toString().split("\\s");
     boolean isSearchItemAvailable = false ;
     for (WebElement webElement : listItems) {
		for (String string : words) {
			isSearchItemAvailable = webElement.getText().contains(string);
			if (!isSearchItemAvailable) {
				continue ;
			}
		}
		
		   Assertion assertion ;
		String productAllProductContainsSearchKeyAssertion = "Verify all Listed Product contains serached item";   
		if (isSearchItemAvailable) {
			System.out.println("All Items Contain Searched Key");
			 assertion = new Assertion(productAllProductContainsSearchKeyAssertion, true, null);
		} else {
			System.out.println("All Items don't Contain Searched Key");
			String screenShotPath = CaptureScreenShot.captureScreenShot(ApplicationConstant.PRODUCT_NOT_CONTAINS_SERCHED_KEY , webDriver);
			assertion = new Assertion(productAllProductContainsSearchKeyAssertion, false, screenShotPath);
		}
		 ReportObj.getReportInstance().addObjToProductList(assertion);
		 
	}
     Thread.sleep(1000);
}

private void  filterbyscreensize() throws InterruptedException {
	Actions a=new Actions(driver);
	a.moveToElement(driver.findElement(By.cssSelector("a[href*='50%2522%2520%252D%252060%2522']"))).click();
	driver.findElement(By.cssSelector("a[href*='50%2522%2520%252D%252060%2522']")).click();
	System.out.println("inside filter");
	 Thread.sleep(1000);
	
}

private void  selectproduct() throws InterruptedException {
	
	selectproduct=driver.findElement(By.className("lvtitle"));
	
	 List<WebElement> listItems = selectproduct.findElements(By.tagName("a")); 
     
     Random random = new Random();
     int randomNumber =  random.nextInt(listItems.size());
     listItems.get(randomNumber).click();
     Thread.sleep(1000);
	System.out.println("Inside product");
	
}
private void  verifyProductCondition() throws InterruptedException {
	
	itemcondion=driver.findElement(By.id("vi-itm-cond"));
	String itemValue=itemcondion.getText();
	
	System.out.println("Inside verifyProductCondition ");
	
	if (itemValue.isEmpty()||itemValue.equals(""))
	{
		System.out.println("Item Condition is Empty");	
	}
	else
	{
		System.out.println("Item Condition is not Empty");
	}
	
	 //Thread.sleep(1000);
}

/*private void  timeLeftFormat() {
	timeleft=driver.findElement(By.id("vi-cdown_timeLeft"));
	Boolean isTimeLeftFormatCorrect= checkTimeLeftFormat(timeleft.getText());
	
	if (isTimeLeftFormatCorrect) {
   	 System.out.println("Time Format Correct");	
   	 
	}else {
		 System.out.println("Time Format Not Correct");	
		}
	}*/
private Boolean checkTimeLeftFormat(String text) {
	
	return true;
	}

private void  verifyPriceFormat() {
	productPrice =driver.findElement(By.id("prcIsum"));
	
	  itemprice=productPrice.getText();
	
 	 System.out.println(itemprice);	
 	 
 	if(itemprice.startsWith(ApplicationConstant.symbol) || itemprice.endsWith(ApplicationConstant.symbol)){
 	    System.out.println("Item Price is Valid");
 	}else{
 	    System.out.println("Item Price is not Valid");
 	}
 	
 	}

private void storeProductDetails()
	{
	
	 System.out.println("Inside storeProductDetails");
	 
	 System.out.println("Inside storeProductDetails" +itemprice);
	 
	productName =driver.findElement(By.id("itemTitle"));
	 prodName=productName.getText();
	 System.out.println("Inside storeProductDetails" +prodName);
		
	 
	 ArrayList<String> productDeatials=new ArrayList<String>();
		productDeatials.add(prodName);
		productDeatials.add(itemprice);
		
	}

private void addtoCart()
{
	
	System.out.println("Inside addtoCart");
	addCart=driver.findElement(By.id("isCartBtn_btn"));
	addCart.click();
	driver.findElement(By.id("addNoThx")).click();
	
}
private void verifyShopppingcart()
{
	
	System.out.println("Inside verifyShopppingcart");
	
	  shoppingCartHeader=driver.findElement(By.id("mainContent"));
	 
	  String cartHeaderpage=shoppingCartHeader.getText();
	  
	  if (cartHeaderpage.equalsIgnoreCase(ApplicationConstant.CartHeader))
	  {
			System.out.println("Your shopping card is open");
	  }
	  else {
		  System.out.println("Your shopping card is open");
	}
	  
	}

private void verifyInformationcart()
{
	
	System.out.println("Inside verifyInformationcart");
	cardProdName=driver.findElement(By.id("282544983816_title"));
	String cardItemName=cardProdName.getText();
	
	System.out.println("step1"+cardItemName);
	
	cardProdPrice=driver.findElement(By.className("fw-b"));
	String cardItemPrice=cardProdPrice.getText();
	
	System.out.println("step2"+cardItemPrice);
	
	totalProdPrice=driver.findElement(By.id("asyncTotal"));
	String totalItemPrice=totalProdPrice.getText();
	
	System.out.println("step3"+totalItemPrice);
	
	if (cardItemName.equalsIgnoreCase(prodName)||cardItemPrice.equals(itemprice)||totalItemPrice.equals(cardItemPrice))
	{
		System.out.println("Information is verfied sussessfuly");
		
		driver.findElement(By.id("ptcBtnBottom")).click();
		
	}
	
	
	
	
}


private void guestCheckout()
{
	driver.findElement(By.id("gtChk")).click();
	
	guestchkoutscreen=driver.findElement(By.className("page-title"));
	
	String guest_screen=guestchkoutscreen.getText();
	
	if(guest_screen.equalsIgnoreCase(ApplicationConstant.checkoutScreenPage))
	{
		System.out.println("Logged in as guest account");
	}
	
	
}
	  
	
}



