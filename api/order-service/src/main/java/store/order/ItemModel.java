package store.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import store.product.ProductOut;

@Entity
@Table(name = "item")
@Setter @Accessors(fluent = true)
@NoArgsConstructor
public class ItemModel {

    @Id
    @Column(name = "id_item")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "id_product")
    private String idProduct;

    @Column(name = "id_order")
    private String idOrder;

    @Column(name = "qtd")
    private Integer qtd;

    @Column(name = "total")
    private Double total;

    public ItemModel(Item i) {
        this.id = i.id();
        this.idProduct = i.product().id();
        this.idOrder = i.order().id();
        this.qtd = i.qtd();
        this.total = i.total();
    }

    public Item to() {
        return Item.builder()
            .id(this.id)
            .product(ProductOut.builder().id(this.idProduct).build())
            .order(Order.builder().id(this.idOrder).build())
            .qtd(this.qtd)
            .total(this.total)
            .build();
    }
    
}
