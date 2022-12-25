package ru.gur.reactor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.gur.reactor.entity.Item;
import ru.gur.reactor.persistence.ItemRepository;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Flux<Item> findItemsByItemCode(String itemCode) {
        return (itemCode != null) ? itemRepository.findByItemCode(itemCode) :
                itemRepository.findAll();
    }

    public Mono<Item> findItemById(long id) {
        return itemRepository.findById(id);
    }

    public Mono<Item> addItem(Item item) {
        return itemRepository.save(item);
    }

    public Mono<Item> updateItem(long id, Item student) {
        return itemRepository.findById(id)
                .flatMap(s -> {
                    s.setItemCode(student.getItemCode());
                    return itemRepository.save(s);
                });
    }

    public Mono<Void> deleteItem(Long id) {
        return itemRepository.deleteById(id);
    }
}
