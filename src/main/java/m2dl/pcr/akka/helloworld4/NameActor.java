package m2dl.pcr.akka.helloworld4;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;


public class NameActor extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private String actorType;

    public NameActor(String type) {
        actorType = type;
        log.info("NameActor constructor. Type : " + type);
    }

    @Override
    public void onReceive(Object msg) throws Exception {

        if (msg instanceof String) {
           log.info(actorType + " " + msg + "!");
        } else {
            unhandled(msg);
        }
    }


}
