package it.oiritaly.batch.configurations;

import org.springframework.batch.item.xml.StaxEventItemWriter;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

/**
 * Created by marco on 14/04/17.
 */
public class CustomStaxEventItemWriter<T> extends StaxEventItemWriter<T> {

    private boolean showRoot;

    public CustomStaxEventItemWriter(boolean showRoot) {
        super();
        this.showRoot = showRoot;
    }


    @Override
    protected void startDocument(XMLEventWriter writer) throws XMLStreamException {
        if (showRoot) {
            super.startDocument(writer);
        } else {

            XMLEventFactory factory = createXmlEventFactory();

            // write start document
            writer.add(factory.createStartDocument(getEncoding(), getVersion()));

		/*
         * This forces the flush to write the end of the root element and avoids
		 * an off-by-one error on restart.
		 */
            writer.add(factory.createIgnorableSpace(""));
            writer.flush();

        }
    }

    @Override
    protected void endDocument(XMLEventWriter writer) throws XMLStreamException {
        return;
    }

}
