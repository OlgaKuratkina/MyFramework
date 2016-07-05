package classes;

import org.testng.annotations.Test;
import pages.TestPage;

/**
 * Created by roma on 6/13/16.
 */
public class Test1 extends TestPage {

    @Test
    public void test1() {
        testGoogleSearch("Macbook");
    }
}
