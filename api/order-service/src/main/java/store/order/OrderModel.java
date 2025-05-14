package store.order;

import java.util.ArrayList;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import store.account.AccountOut;

@Entity
@Table(name = "orders")
@Setter @Accessors(fluent = true)
@NoArgsConstructor
public class OrderModel {

    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "id_user")
    private String userId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_order")
    private Date date;

    @Column(name = "total")
    private Double total;

    public OrderModel(Order o) {
        this.id = o.id();
        this.userId = o.account().id();
        this.date = o.date();
        this.total = o.total();
    }

    public Order to() {
        return Order.builder()
            .id(this.id)
            .account(AccountOut.builder().id(this.userId).build())
            .date(this.date)
            .total(this.total)
            .itens(new ArrayList<>())
            .build();
    }
    
}
