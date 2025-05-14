package store.order;

import java.text.SimpleDateFormat;

public class OrderParser {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Order to(OrderIn in) {
        return in == null ? null :
            Order.builder()
                .itens(in.itens().stream().map(ItemParser::to).toList())
                .build();
    }

    public static OrderOut to(Order o) {
        return o == null ? null :
            OrderOut.builder()
                .id(o.id())
                .itens(o.itens().stream().map(ItemParser::to).toList())
                .date(sdf.format(o.date()))
                .total(o.total())
                .build();
    }
    
}
