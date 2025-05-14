package store.order;

import java.util.Date;
import java.util.List;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import store.product.ProductController;
import store.product.ProductOut;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

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
            logger.debug(item.toString());
            item.total(item.qtd() * item.product().price());
            logger.debug(item.toString());

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

    public List<Order> findAll() {

        List<Order> orders = StreamSupport
            .stream(orderRepository.findAll().spliterator(), false)
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

    public Order findById(String id) {

        Order order = orderRepository.findById(id).orElse(null).to();
        if (order == null) return null;

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
