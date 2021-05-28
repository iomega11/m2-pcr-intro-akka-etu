package m2dl.pcr.akka.eratosthene;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;


public class Filter extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private final int number;
    private ActorRef nextFilter;

    public Filter(int number) {
        this.number = number;
    }

    @Override
    public void onReceive(Object value) {
        if (value instanceof Integer) {
            int valueAsInt = (int) value;
            if (valueAsInt % number != 0) {
                if (nextFilter == null) {
                    System.out.print(" " + valueAsInt);
                    nextFilter = getContext().actorOf(Props.create(Filter.class, valueAsInt), "filter-" + valueAsInt);
                }
                nextFilter.tell(valueAsInt, getSelf());
            }
        } else if (value == "STOP") {
            log.info("Arrêt du Filtre " + number);
            nextFilter.tell("STOP", getSelf());
            getContext().stop(getSelf());
        } else {
            unhandled(value);
        }
    }
}

