package local.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;

public class UnionFlux {

   @Test
   public void mergeFluxes() {
      Flux<String> flux1 = Flux.just("Alberto", "Juan Diego", "Santiago", "Julián")
         .delayElements(Duration.ofMillis(500));
      Flux<String> flux2 = Flux.just("Lasagna", "Lollipods", "Apples")
         .delaySubscription(Duration.ofMillis(250))
         .delayElements(Duration.ofMillis(500));

      Flux<String> mergeFlux = flux1.mergeWith(flux2);
      mergeFlux.subscribe(System.out::println);

      StepVerifier.create(mergeFlux)
         .expectNext("Alberto")
         .expectNext("Lasagna")
         .expectNext("Juan Diego")
         .expectNext("Lollipods")
         .expectNext("Santiago")
         .expectNext("Apples")
         .expectNext("Julián")
         .verifyComplete();
   }

   @Test
   public void zipFluxesToTuple() {
      Flux<String> flux1 = Flux.just("Alberto", "Juan Diego", "Santiago", "Julián")
         .delayElements(Duration.ofMillis(500));
      Flux<String> flux2 = Flux.just("Lasagna", "Lollipods", "Apples")
         .delaySubscription(Duration.ofMillis(250))
         .delayElements(Duration.ofMillis(500));
      // If there are differences in dim, it just truncates list size to the smallest
      Flux<Tuple2<String,String>> zippedFlux = Flux.zip(flux1, flux2);
      zippedFlux.subscribe(System.out::println);

      StepVerifier.create(zippedFlux)
         .expectNextMatches(p -> p.getT1().equals("Alberto") && p.getT2().equals("Lasagna"))
         .expectNextMatches(p -> p.getT1().equals("Juan Diego") && p.getT2().equals("Lollipods"))
         .expectNextMatches(p -> p.getT1().equals("Santiago") && p.getT2().equals("Apples"))
         .verifyComplete();
   }

   @Test
   public void zipFluxesToString() {
      Flux<String> flux1 = Flux.just("Alberto", "Juan Diego", "Santiago", "Julián");
      Flux<String> flux2 = Flux.just("Lasagna", "Lollipods", "Apples");

      Flux<String> zippedFlux = Flux.zip(flux1, flux2, (f1, f2) -> f1 + " eats " + f2);
      zippedFlux.subscribe(System.out::println);

      StepVerifier.create(zippedFlux)
         .expectNext("Alberto eats Lasagna")
         .expectNext("Juan Diego eats Lollipods")
         .expectNext("Santiago eats Apples")
         .verifyComplete();
   }

   @Test
   public void firstFlux() {
      Flux<String> fastFlux = Flux.just("Salmón", "Carne de Res", "Pollo");
      Flux<String> slowFlux = Flux.just("Queso", "Bocadillo")
         .delaySubscription(Duration.ofMillis(250));

      Flux<String> firstFlux = Flux.firstWithSignal(fastFlux, slowFlux);
      firstFlux.subscribe(System.out::println);

      StepVerifier.create(firstFlux)
         .expectNext("Salmón")
         .expectNext("Carne de Res")
         .expectNext("Pollo")
         .verifyComplete();
   }

}
