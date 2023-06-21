package local.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BufferFlux {

   @Test
   public void bufferFlux() {
      Flux<String> fruitFlux = Flux.just("apple", "orange", "banana", "kiwi", "strawberry");

      Flux<List<String>> bufferedFlux = fruitFlux.buffer(3);
      bufferedFlux.subscribe(System.out::println);

      StepVerifier.create(bufferedFlux)
         .expectNext(Arrays.asList("apple", "orange", "banana"))
         .expectNext(Arrays.asList("kiwi", "strawberry"))
         .verifyComplete();
   }

   @Test
   public void parallelBufferFlux() {
      Flux
         .just("apple", "orange", "banana", "kiwi", "strawberry")
         .buffer(3)
         .flatMap(
            fruitList -> Flux.fromIterable(fruitList).map(String::toUpperCase).subscribeOn(Schedulers.parallel()).log()
         )
         .subscribe();
   }

   @Test
   public void collectToList() {
      Flux<String> fruitFlux = Flux.just("apple", "orange", "banana", "kiwi", "strawberry");

      Mono<List<String>> fruitListMono = fruitFlux.collectList();

      StepVerifier.create(fruitListMono)
         .expectNext(Arrays.asList("apple", "orange", "banana", "kiwi", "strawberry"))
         .verifyComplete();
   }

   @Test
   public void collectToMap() {
      Flux<String> fruitFlux = Flux.just("apple", "orange", "banana", "kiwi", "strawberry");

      Mono<Map<Character,String>> fruitMapMono = fruitFlux.collectMap(fruit -> fruit.charAt(0));
      fruitMapMono.subscribe(System.out::println);

      StepVerifier.create(fruitMapMono)
         .expectNextMatches(map -> map.size() == 5 &&
         map.get('a').equals("apple") &&
         map.get('o').equals("orange") &&
         map.get('b').equals("banana") &&
         map.get('k').equals("kiwi") &&
         map.get('s').equals("strawberry"))
         .verifyComplete();
   }

}
