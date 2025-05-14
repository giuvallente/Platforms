package store.order;

import java.util.Date;
import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import store.product.ProductController;
import store.product.ProductOut;

@Service
public class OrderService {

    @Autowired
    private ProductController productController;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Order create(Order order) {
        order.date(new Date());
        order.total(0.0);

        order.itens().forEach(item -> {
            ProductOut product = productController.findProduct(item.product().id()).getBody();
            item.product(product);
            item.total(item.qtd() * item.product().price());

            order.total(order.total() + item.total());
        });

        Order saved = orderRepository.save(new OrderModel(order)).to();

        order.itens().forEach(item -> {
            item.order(saved);
            Item savedItem = itemRepository.save(new ItemModel(item)).to();
            saved.itens().add(savedItem.product(item.product()));
        });

        return saved;
    }

    public List<Order> findAll(String idAccount) {

        List<Order> orders = StreamSupport
            .stream(orderRepository.findByUserId(idAccount).spliterator(), false)
            .map(OrderModel::to)
            .toList();
        
        orders.forEach(order -> {
            order.itens(
                StreamSupport
                .stream(itemRepository.findByIdOrder(order.id()).spliterator(), false)
                .map(ItemModel::to)
                .toList()
            );
        });
        
        return orders;
    }

    public Order findById(String idAccount, String id) {

        Order order = orderRepository.findById(id).orElse(null).to();
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        };

        if (!order.account().id().equals(idAccount)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }

        order.itens(
            StreamSupport
            .stream(itemRepository.findByIdOrder(id).spliterator(), false)
            .map(ItemModel::to)
            .toList()
        );

        return order;
    }

    public void deleteOrder(String id) {
        OrderModel order = orderRepository.findById(id).get();
        orderRepository.delete(order);
    }
    
}
