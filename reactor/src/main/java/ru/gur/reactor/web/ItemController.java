package ru.gur.reactor.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.gur.reactor.entity.Item;
import ru.gur.reactor.service.ItemService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Item>> getItem(@PathVariable long id) {
        return itemService.findItemById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<Item> getItems(@RequestParam(name = "itemCode", required = false) String itemCode) {
        return itemService.findItemsByItemCode(itemCode);
    }

    @PostMapping
    public Mono<Item> addItem(@RequestBody Item item) {
        return itemService.addItem(item);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Item>> updateStudent(@PathVariable long id, @RequestBody Item item) {
        return itemService.updateItem(id, item)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteStudent(@PathVariable long id) {
        return itemService.findItemById(id)
                .flatMap(s ->
                        itemService.deleteItem(id)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
