package labdsoft.template_mcs.services;


import labdsoft.template_mcs.model.Product;
import labdsoft.template_mcs.model.ProductDTO;
import labdsoft.template_mcs.model.ProductDetailDTO;

import java.util.Optional;

public interface ProductService {
    ProductDTO create(final Product manager) throws Exception;

    Optional<ProductDTO> findBySku(final String sku);

    Optional<Product> getProductBySku(final String sku );

    Iterable<ProductDTO> findByDesignation(final String designation);

    Iterable<ProductDTO> getCatalog();

    ProductDetailDTO getDetails(final String sku);

    void createFromSubscribe(final ProductDetailDTO productDetailDTO) throws Exception;

    void updateBySkuFromSubscribe(final String sku, final ProductDetailDTO productDetailDTO) throws Exception;

    void deleteBySkuFromSubscribe(final String sku) throws Exception;

    void deleteAll() throws Exception;

}
