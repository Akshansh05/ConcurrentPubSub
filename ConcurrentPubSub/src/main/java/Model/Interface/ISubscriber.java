package Model.Interface;

import Model.Message;

public interface ISubscriber {

    String getId();

    void consume(Message message) throws InterruptedException;
}
