package test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.xmlunit.builder.Input;

import static org.junit.Assert.assertThat;
import static org.xmlunit.matchers.ValidationMatcher.valid;


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {
        BatchTestConfiguration.class})
public class XmlExportAmazonJobTests {
    @Autowired
    private JobLauncher jobLauncher;

    @Test
    public void testDbToXmlAmazonInventoryFeed() throws Exception {
        //assertThat(1L, is(1L));


        assertThat(Input.fromFile("../outputs/amazon/DbToXmlAmazonInventoryFeed.xml"), valid(Input.fromURI("https://images-na.ssl-images-amazon.com/images/G/01/rainier/help/xsd/release_1_9/amzn-envelope.xsd")));

    }
}
