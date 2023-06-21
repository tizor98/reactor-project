package local.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class MappingData {

   @Test
   public void flatMapData() {
      Flux<Player> playerFlux = Flux.just("Michael Jordan", "Cristiano Ronaldo", "Alberto Ortiz")
         .flatMap(n -> Mono.just(n).map(fn -> {
            String[] fullNames = fn.split("\\s");
            return new Player(fullNames[0], fullNames[1]);
         }).subscribeOn(Schedulers.parallel())
         );

      List<Player> playerList = Arrays.asList(
         new Player("Michael", "Jordan"),
         new Player("Cristiano", "Ronaldo"),
         new Player("Alberto", "Ortiz")
      );

      StepVerifier.create(playerFlux)
         .expectNextMatches(playerList::contains)
         .expectNextMatches(playerList::contains)
         .expectNextMatches(playerList::contains)
         .verifyComplete();
   }

   @Test
   public void mapData() {
      Flux<Player> playerFlux = Flux.just("Michael Jordan", "Cristiano Ronaldo", "Alberto Ortiz")
         .map(fn -> {
            String[] fullNames = fn.split("\\s");
            return new Player(fullNames[0], fullNames[1]);
         });

      StepVerifier.create(playerFlux)
         .expectNext(new Player("Michael", "Jordan"))
         .expectNext(new Player("Cristiano", "Ronaldo"))
         .expectNext(new Player("Alberto", "Ortiz"))
         .verifyComplete();
   }

   private record Player(String firstName, String lastName) {
   }

}
