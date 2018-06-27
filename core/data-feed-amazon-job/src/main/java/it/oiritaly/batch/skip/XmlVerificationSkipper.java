package it.oiritaly.batch.skip;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;

@Slf4j
public class XmlVerificationSkipper implements SkipPolicy {

    private static final Logger logger = LoggerFactory.getLogger("XmlVerificationSkipper");
    @Override
    public boolean shouldSkip(Throwable exception, int skipCount) throws SkipLimitExceededException {
        logger.info("Is the exception "+exception.getClass()+" skippable?");
        if (exception instanceof FileNotFoundException) {
            return false;
        } else if (exception instanceof XMLStreamException && skipCount <= 5) {
            XMLStreamException ffpe = (XMLStreamException) exception;
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("An error occured while processing the " + ffpe.getLocation()
                    + " line of the file. Below was the faulty " + "input.\n");
            logger.error("{}", errorMessage.toString());
            return true;
        } else {
            logger.info("The exception "+exception.getClass()+" is not skippable");
            logger.info("Saving "+exception.getLocalizedMessage());
            return true;
        }
    }

}
