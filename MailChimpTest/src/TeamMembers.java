import java.io.FileWriter;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TeamMembers {

	public static void main(String[] args) throws Exception {
		System.setProperty("webdriver.gecko.driver", "./geckodriver.exe");		
		WebDriver driver = null;
		FileWriter fileWriter = null;
		
		try {
			driver = new FirefoxDriver(); 
			fileWriter = new FileWriter("./output.csv"); 
			
	        // open the webpage and navigate to About page
			driver.navigate().to("https://mailchimp.com/"); 
	     	driver.findElement(By.linkText("About MailChimp")).click();
	     	Thread.sleep(1000);
	     	
	     	// find names of all team members from leadership
	     	String data = driver.findElement(By.cssSelector(".row.align-left.bio_wrapper")).getText();
	     	String nameList[] = data.split("\\r\\n|\\n|\\r");
	     	
	     	fileWriter.append("Name");
	     	fileWriter.append(",");
	     	fileWriter.append("Position");
	     	fileWriter.append(",");
	     	fileWriter.append("Description");
	     	fileWriter.append("\n");
	     	
	     	// Loop through all the team members and write their details to csv file
	     	for (int i=0; i<nameList.length; i = i+2) {
		     	driver.findElement(By.xpath("//*[contains(@class,'mb1') and text()='"+nameList[i]+"']")).click();
		     	Thread.sleep(1000);
		     	String description = driver.findElement(By.id("bio_view")).getText();
		     	driver.findElement(By.cssSelector(".close_btn.icon-close-large")).click();
		     	Thread.sleep(1000);
		     	
		     	String temp[] = description.split("\\r\\n|\\n|\\r"); 
		     	fileWriter.append(temp[0]); 
		     	fileWriter.append(",");
		     	fileWriter.append(temp[1]); 
		     	fileWriter.append(",");
		     	fileWriter.append("\"" + temp[2].replaceAll("\"", "\"\"") + "\"");
		     	fileWriter.append("\n");
	     	}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			driver.quit();
			try {
		     	fileWriter.flush();
		        fileWriter.close();
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
	}
}
