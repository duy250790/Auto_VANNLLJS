package base;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    WebDriver driver;

    @AfterMethod
    public void TearDown(ITestResult result) throws IOException {
        String nameMethod = result.getMethod().getMethodName();
        if(!result.isSuccess()){
            System.out.printf("Test : %s is FAIL\n",nameMethod);
            nameMethod = nameMethod + "-"+ System.currentTimeMillis();
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File(String.format("./target/screen-shots/%s.png", nameMethod)));
        }
        else {
            System.out.printf("Test : %s is SUCCESS\n",nameMethod);
        }
    }
}
