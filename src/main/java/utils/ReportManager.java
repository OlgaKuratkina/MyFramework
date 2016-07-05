package utils;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReportManager {

    private static Map<Long, ExtentTest> testThread = new HashMap<Long, ExtentTest>();
    private static ExtentReports extentReports;

    private synchronized static ExtentReports getInstance() {
        if (extentReports == null) {
            extentReports = new ExtentReports("./src/test/results/reports/Report.html", true);
        }
        return extentReports;
    }

    public synchronized static Map<Long, ExtentTest> startTest(String testName, String testDescription, String... groups) {
        Long threadID = Thread.currentThread().getId();
        ExtentTest test = getInstance().startTest(testName, testDescription);
        test.assignCategory(groups);
        testThread.put(threadID, test);
        return testThread;
    }

    public synchronized static ExtentTest myLogger() {
        ExtentTest logger = null;
        Long threadID = Thread.currentThread().getId();
        if (testThread.containsKey(threadID)) {
            logger = testThread.get(threadID);
        }
        return logger;
    }

    public synchronized static void closeTest() {
        getInstance().endTest(myLogger());
    }

    public synchronized static void closeReporter() {
        getInstance().flush();
    }

    public static String getTestName(Method m) {
        String testNme = null;
        String address = null;
        String[] testGroups = m.getAnnotation(Test.class).groups();
        for (int i = 0; i < testGroups.length; i++) {
            if (testGroups[i].startsWith("http")) {
                address = testGroups[i];
            }
        }
        if (address == null) {
            testNme = "<a href=" + "\"" + address + "\""
                    + "target=_blank alt=This test is linked to test case. Click to open it>"
                    + m.getAnnotation(Test.class).testName() + "</a>";
        } else {
            testNme = m.getAnnotation(Test.class).testName();
        }
        return testNme;
    }

    private String getTestDescription(Method method) {
        String testDescription = null;
        testDescription = method.getAnnotation(Test.class).description();
        if (testDescription == null || testDescription == "") {
            testDescription = "";
        }
        return testDescription;
    }

    private String getGroups(Method method) {
        String group = "";
        String[] testGroup = method.getAnnotation(Test.class).groups();
        try {
            for (int i = 0; i < testGroup.length; i++) {
                if (testGroup[i].startsWith("http")) {
                    continue;
                } else {
                    group += " " + testGroup[i] + ";";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return group;
    }

    @BeforeMethod(alwaysRun = true)
    public void startReporting(Method method) {
        startTest(getTestName(method), getTestDescription(method), WebBrowser.getCurrentBrovserName(), getGroups(method));

        System.out.println(
                "--------------------------------------------------------------------------------------------------------");
        System.out.println("[INFO] Started test '" + getTestName(method) + "' Current browser '"
                + WebBrowser.getCurrentBrovserName() + "' Groups: '" + getGroups(method).trim() + "'");
    }

    @AfterMethod(alwaysRun = true)
    public void stopReporting(ITestResult result) {
        closeTest();
        int res = result.getStatus();
        switch (res) {
            case 1:
                System.out.println("[INFO] Test method finished with status: PASSED");
                break;
            case 2:
                System.out.println("[FAILED] Test method finished with status: FAILED");
                break;
            case 3:
                System.out.println("[INFO] Test method finished with status: SKIPED");
                break;
        }
    }

}
