package store.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductResource implements ProductController {

    @Autowired
    private ProductService productService;

    @Override
    public ResponseEntity<ProductOut> create(ProductIn productIn) {
        Product created = productService.create(ProductParser.to(productIn));
        return ResponseEntity.ok().body(ProductParser.to(created));
    }
    
    @Override
    public ResponseEntity<List<ProductOut>> findAll() {
        return ResponseEntity
            .ok()
            .body(productService.findAll().stream().map(ProductParser::to).toList());
    }

    @Override
    public ResponseEntity<ProductOut> findProduct(String id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok().body(ProductParser.to(product));
    }

    @Override
    public void deleteProduct(String id) {
        productService.deleteProduct(id);
    }
}
