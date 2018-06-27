package it.oiritaly.batch.readers;

import it.oiritaly.data.models.jpa.DataFeed;
import it.oiritaly.data.repositories.jpa.DataFeedRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

public class DataFeedItemReader implements ItemReader<DataFeed> {

    @Autowired
    private DataFeedRepository dataFeedRepository;
    private boolean alreadyRead = false;

    @Override
    public DataFeed read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (alreadyRead)
            return null;
        alreadyRead = true;
        return  dataFeedRepository.findTop1ByOrderByParseDateDateTimeDesc();
    }
}
