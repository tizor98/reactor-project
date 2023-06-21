package local.reactor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.aggregator.AggregateWith;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class LogicalTestFlux {

   @Test
   public void allMeetSomeCriteria() {
      Flux<String> animalFlux = Flux.just("elephant", "koala", "eagle", "kangaroo");

      Mono<Boolean> hasAMono = animalFlux.all(a -> a.contains("a"));

      StepVerifier.create(hasAMono)
         .expectNext(true)
         .verifyComplete();

      Mono<Boolean> hasKMono = animalFlux.all(a -> a.contains("k"));

      StepVerifier.create(hasKMono)
         .expectNext(false)
         .verifyComplete();
   }

   @Test
   public void anyMeetSomeCriteria() {
      Flux<String> animalFlux = Flux.just("elephant", "koala", "eagle", "kangaroo");

      Mono<Boolean> hasAMono = animalFlux.any(a -> a.contains("t"));

      StepVerifier.create(hasAMono)
         .expectNext(true)
         .verifyComplete();

      Mono<Boolean> hasKMono = animalFlux.any(a -> a.contains("z"));

      StepVerifier.create(hasKMono)
         .expectNext(false)
         .verifyComplete();
   }

}
