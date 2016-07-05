package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Screenshot extends WebBrowser {

    public static String take() {
        Date date = new Date();
        String path = "./src/test/results/screenshots/" + date.getTime() + ".png";
        TakesScreenshot takesScreenshot = (TakesScreenshot) Driver();
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(source, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

}
