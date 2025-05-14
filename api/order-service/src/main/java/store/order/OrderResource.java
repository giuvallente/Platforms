package store.order;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import store.account.AccountOut;

@RestController
public class OrderResource implements OrderController{

    private static final Logger logger = LoggerFactory.getLogger(OrderResource.class);

    @Autowired
    private OrderService orderService;

    @Override
    public ResponseEntity<OrderOut> create(String idAccount, OrderIn orderIn) {
        logger.debug(orderIn.toString());
        Order created = orderService.create(
            OrderParser.to(orderIn)
            .account(AccountOut.builder().id(idAccount).build()));
        return ResponseEntity.ok().body(OrderParser.to(created));
    }  

    @Override
    public ResponseEntity<List<OrderOut>> findAll(String idAccount) {
        return ResponseEntity
            .ok()
            .body(orderService.findAll().stream().map(OrderParser::to).toList());
    }

    @Override
    public ResponseEntity<OrderOut> findOrder(String idAccount, String id) {
        Order order = orderService.findById(id);
        return ResponseEntity.ok().body(
            OrderParser.to(order)
        );
    }

    @Override
    public void deleteOrder(String idAccount, String id) {
        orderService.deleteOrder(id);
    }
}
