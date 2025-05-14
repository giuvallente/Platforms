package store.order;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import store.account.AccountOut;

@Builder
@Data @Accessors(fluent = true)
public class Order {

    private String id;
    private AccountOut account;
    private Date date;
    private Double total;
    private List<Item> itens;

}