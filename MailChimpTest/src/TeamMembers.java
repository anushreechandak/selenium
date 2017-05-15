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
	
			driver.navigate().to("https://mailchimp.com/");
	     	driver.findElement(By.linkText("About MailChimp")).click();
	     	Thread.sleep(1000);
	     	String data = driver.findElement(By.cssSelector(".row.align-left.bio_wrapper")).getText();
	     	String textStr[] = data.split("\\r\\n|\\n|\\r");
	     	
	     	fileWriter.append("Name");
	     	fileWriter.append(",");
	     	fileWriter.append("Position");
	     	fileWriter.append(",");
	     	fileWriter.append("Description");
	     	fileWriter.append("\n");
	     	
	     	for (int i=0; i<textStr.length; i = i+2) {
		     	driver.findElement(By.xpath("//*[contains(@class,'mb1') and text()='"+textStr[i]+"']")).click();
		     	Thread.sleep(1000);
		     	String data1 = driver.findElement(By.id("bio_view")).getText();
		     	driver.findElement(By.cssSelector(".close_btn.icon-close-large")).click();
		     	Thread.sleep(1000);
	
		     	String text[] = data1.split("\\r\\n|\\n|\\r");
		     	fileWriter.append(text[0]);
		     	fileWriter.append(",");
		     	fileWriter.append(text[1]);
		     	fileWriter.append(",");
		     	fileWriter.append("\"" + text[2].replaceAll("\"", "\"\"") + "\"");
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
