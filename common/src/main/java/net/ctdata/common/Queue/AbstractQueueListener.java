package net.ctdata.common.Queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import net.ctdata.common.Messages.AbstractMessage;
import net.ctdata.common.Messages.HistoryResponse;

import java.lang.reflect.ParameterizedType;
import java.util.TimeZone;

abstract class AbstractQueueListener<T> implements QueueListener {
    Class<T> messageClass;

    /**
     * Because Java is a stupid language, we need the type of the message passed in in the constructor
     *
     * @see <a href="http://stackoverflow.com/questions/3403909/get-generic-type-of-class-at-runtime">Stack overflow</a>
     * @param messageClass T.class
     */
    public AbstractQueueListener(Class<T> messageClass){
        this.messageClass = messageClass;
    }

    @Override
    public void HandleMessage(Message message) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        mapper.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            T response = (T)mapper.readValue(message.getBody(), messageClass);
            HandleMessage(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * The method which should be overridden in the client code
     *
     * @param message The message, deserialized from JSON. Subtype of {@link AbstractMessage}
     */
    public abstract void HandleMessage(T message);
}
