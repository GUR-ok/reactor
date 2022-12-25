package ru.gur.reactor.persistence;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.gur.reactor.entity.Item;

@Repository
public interface ItemRepository extends ReactiveCrudRepository<Item, Long> {
    @Query("SELECT * FROM items WHERE item_code = :itemCode")
    Flux<Item> findByItemCode(String itemCode);
}
