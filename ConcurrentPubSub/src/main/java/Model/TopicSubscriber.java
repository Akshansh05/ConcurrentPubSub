package Model;

import Model.Interface.ISubscriber;
import lombok.Data;
import lombok.NonNull;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class TopicSubscriber {

    private final ISubscriber iSubscriber;
    private final AtomicInteger offset;


    public TopicSubscriber(@NonNull final ISubscriber iSubscriber) {
        this.iSubscriber = iSubscriber;
        offset = new AtomicInteger(0);
    }


}
