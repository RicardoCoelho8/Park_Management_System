package labdsoft.template_mcs.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductDetailDTO {
    private String sku;
    private String designation;
    private String description;

    public ProductDetailDTO() {
    }

    public ProductDetailDTO(String sku, String designation, String description) {
        this.sku = sku;
        this.designation = designation;
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product fromDTO() {
        return new Product(this.getSku(), this.getDesignation(), this.getDescription());
    }

    public ProductDetailDTO fromString(String objectString) {
        String[] items = objectString.replace("{", "").split(",");

        this.sku = items[1].split("=")[1];
        this.designation = items[2].split("=")[1];
        this.description = items[3].split("=")[1].replace(")", "");

        return this;
    }

    public static List<ProductDetailDTO> fromStringToList(String objectString) {
        List<ProductDetailDTO> productList = new ArrayList<>();
        String productsWithoutBrackets = objectString.replaceAll("\\[|\\]", "");
        String[] productString = productsWithoutBrackets.split("\\), ");

        for (String productDto : productString) {
                String[] items = productDto.split(",");
                String sku = items[0].split("=")[1];
                String designation = items[1].split("=")[1];
                String description = items[2].split("=")[1].replace(")", "");
                productList.add(new ProductDetailDTO(sku, designation, description));
        }
        return productList;
    }
}
