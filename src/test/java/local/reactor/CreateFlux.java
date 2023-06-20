package local.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class CreateFlux {

   @Test
   public void fromJust() {
      Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Banana");
      fruitFlux.subscribe(f -> System.out.println("Here's some fruit: " + f));

      StepVerifier.create(fruitFlux)
         .expectNext("Apple")
         .expectNext("Orange")
         .expectNext("Grape")
         .expectNext("Banana")
         .verifyComplete();
   }

   @Test
   public void fromArray() {
      Integer[] intArray = {1,2,3,4};
      Flux<Integer> integerFlux = Flux.fromArray(intArray);

      StepVerifier.create(integerFlux)
         .expectNext(1)
         .expectNext(2)
         .expectNext(3)
         .expectNext(4)
         .verifyComplete();
   }

   @Test
   public void fromIterable() {
      List<String> list = Arrays.asList("Apple", "Orange", "Grape", "Banana");

      Flux<String> stringFlux = Flux.fromIterable(list);

      StepVerifier.create(stringFlux)
         .expectNext("Apple")
         .expectNext("Orange")
         .expectNext("Grape")
         .expectNext("Banana")
         .verifyComplete();
   }

   @Test
   public void fromStream() {
      Stream<String> stream = Stream.of("Apple", "Orange", "Grape", "Banana");

      Flux<String> stringFlux = Flux.fromStream(stream);

      StepVerifier.create(stringFlux)
         .expectNext("Apple")
         .expectNext("Orange")
         .expectNext("Grape")
         .expectNext("Banana")
         .verifyComplete();
   }

   @Test
   public void fromRange() {
      Flux<Integer> flux = Flux.range(6,5);

      StepVerifier.create(flux)
         .expectNext(6)
         .expectNext(7)
         .expectNext(8)
         .expectNext(9)
         .expectNext(10)
         .verifyComplete();
   }

   @Test
   public void fromInterval() {
      Flux<Long> flux = Flux.interval(Duration.ofSeconds(1)).take(5);

      StepVerifier.create(flux)
         .expectNext(0L)
         .expectNext(1L)
         .expectNext(2L)
         .expectNext(3L)
         .expectNext(4L)
         .verifyComplete();
   }

}
