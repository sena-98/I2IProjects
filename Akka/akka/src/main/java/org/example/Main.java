package org.example;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.PatternsCS;
import akka.util.Timeout;
import scala.concurrent.duration.Duration;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Create the actor system
        ActorSystem system = ActorSystem.create("firstActorSystem");

        // Create the first actor
        ActorRef firstActor = system.actorOf(FirstActor.props(), "firstActor");

        // Create the second actor
        ActorRef secondActor = system.actorOf(SecondActor.props(), "secondActor");

        // Send a message from the first actor to the second actor and wait for the response
        CompletionStage<Object> responseFuture = PatternsCS.ask(secondActor, new SecondActor.HelloFromSecondActor(), Timeout.apply(Duration.create(5, TimeUnit.SECONDS)));

        responseFuture.whenComplete((response, throwable) -> {
            if (response instanceof String) {
                System.out.println("Response from SecondActor: " + response);
            } else {
                System.out.println("Failed to get response from SecondActor.");
            }
        });

        // Let the application run for a while
        Thread.sleep(1000);

        // Terminate the actor system
        system.terminate();
    }
}
