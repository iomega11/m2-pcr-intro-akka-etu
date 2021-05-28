package m2dl.pcr.akka.eratosthene;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Main runtime class.
 */
public class EratostheneSystem {

    public static final Logger log = LoggerFactory.getLogger(EratostheneSystem.class);

    public static void main(String... args) throws Exception {

        if(args.length != 1) {
            System.out.println("Argument required!");
            System.exit(-1);
        }

        final int N = Integer.parseInt(args[0]);

        final ActorSystem actorSystem = ActorSystem.create("actor-system");

        Thread.sleep(5000);

        final ActorRef actorRef = actorSystem.actorOf(Props.create(Filter.class, 2), "filter-1");

        for(int i = 3; i < N; i++) {
            actorRef.tell(i,null);
        }

        Thread.sleep(1000);

        System.out.println();
        log.debug("Actor System Shutdown Starting...");
        actorRef.tell("STOP", null);
        actorSystem.terminate();
    }
}
