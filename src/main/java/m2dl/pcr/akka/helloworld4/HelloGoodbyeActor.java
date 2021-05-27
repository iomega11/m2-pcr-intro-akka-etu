package m2dl.pcr.akka.helloworld4;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;


public class HelloGoodbyeActor extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private final ActorRef helloActorRef;
    private final ActorRef goodbyeActorRef;

    public HelloGoodbyeActor() {
        log.info("HelloGoodbyeActor constructor");
        helloActorRef = getContext().actorOf(Props.create(NameActor.class, "Hello"), "hello-name-actor");
        goodbyeActorRef = getContext().actorOf(Props.create(NameActor.class, "Goodbye"), "goodbye-name-actor");
    }

    Procedure<Object> hello = new Procedure<Object>() {
        public void apply(Object msg) {
            if (msg instanceof String) {
                helloActorRef.tell(msg,getSelf());
                getContext().become(goodbye,false);
            } else {
                unhandled(msg);
            }
        }
    };

    Procedure<Object> goodbye = new Procedure<Object>() {
        public void apply(Object msg) {
            if (msg instanceof String) {
                goodbyeActorRef.tell(msg,getSelf());
                getContext().become(hello,false);
            } else {
                unhandled(msg);
            }
        }
    };

    @Override
    public void onReceive(Object msg) throws Exception {
        hello.apply(msg);
    }


}
