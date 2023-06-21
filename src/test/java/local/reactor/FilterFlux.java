package local.reactor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FilterFlux {

   @Test
   public void filter() {
      Flux<String> countFlux = Flux.just("one", "two", "three", "four", "five")
         .filter(c -> !c.contains("t"));
      countFlux.subscribe(System.out::println);

      StepVerifier.create(countFlux)
         .expectNext("one", "four", "five")
         .verifyComplete();
   }

   @Test
   public void distinctElements() {
      Flux<String> countFlux = Flux.just("one", "two", "one", "four", "two")
         .distinct();
      countFlux.subscribe(System.out::println);

      StepVerifier.create(countFlux)
         .expectNext("one", "two", "four")
         .verifyComplete();
   }

   @Test
   public void skipAFew() {
      Flux<String> countFlux = Flux.just("one", "two", "three", "four", "five")
         .skip(3);
      countFlux.subscribe(System.out::println);

      StepVerifier.create(countFlux)
         .expectNext("four", "five")
         .verifyComplete();
   }

   @Test
   public void skipForATime() {
      Flux<String> countFlux = Flux.just("one", "two", "three", "four", "five")
         .delayElements(Duration.ofMillis(500))
         .skip(Duration.ofSeconds(2));
      countFlux.subscribe(System.out::println);

      StepVerifier.create(countFlux)
         .expectNext("four", "five")
         .verifyComplete();
   }

   @Test
   public void takeForATime() {
      Flux<String> countFlux = Flux.just("one", "two", "three", "four", "five")
         .delayElements(Duration.ofMillis(500))
         .take(Duration.ofSeconds(2));
      countFlux.subscribe(System.out::println);

      StepVerifier.create(countFlux)
         .expectNext("one", "two", "three")
         .verifyComplete();
   }

}
