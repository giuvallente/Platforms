package store.order;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "order", url = "http://order:8080")
public interface OrderController {

    @PostMapping("/order")
    public ResponseEntity<OrderOut> create(
        @RequestHeader(value = "id-account", required = true) String idAccount,
        @RequestBody OrderIn orderIn
    );

    @GetMapping("/order")
    public ResponseEntity<List<OrderOut>> findAll(
        @RequestHeader(value = "id-account", required = true) String idAccount
    );

    @GetMapping("/order/{id}")
    public ResponseEntity<OrderOut> findOrder(
        @RequestHeader(value = "id-account", required = true) String idAccount,
        @PathVariable String id
    );

    @DeleteMapping("/order/{id}")
    public void deleteOrder(
        @RequestHeader(value = "id-account", required = true) String idAccount,
        @PathVariable String id
    );
}