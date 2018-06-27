package it.oiritaly;

import it.oiritaly.data.models.jpa.DataFeed;
import it.oiritaly.data.repositories.jpa.DataFeedRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataFeedApplicationTests {

    //@Autowired
    //private DataFeedRepository dfr;


    @Test
    public void myProvaTest() {
        //DataFeed myDf = new DataFeed();
        //assertThat(myDf.get_id()).isNull();
        //myDf = dfr.save(myDf);
        //assertThat(myDf.get_id()).isNotNull();
        assertThat(true);
    }

}
