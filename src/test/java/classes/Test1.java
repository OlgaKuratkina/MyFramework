package classes;

import org.testng.annotations.Test;
import pages.TestPage;

public class Test1 extends TestPage {


//    Simple test

    @Test
    public void test1() {
        testGoogleSearch("Macbook");
    }
}
