package store.order;

import lombok.Builder;
import lombok.experimental.Accessors;

@Builder @Accessors(fluent = true)
public record ItemIn (
    String idProduct,
    Integer qtd
) {

}
