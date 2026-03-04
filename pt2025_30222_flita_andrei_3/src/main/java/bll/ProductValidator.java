package bll;

import model.Product;

public class ProductValidator implements Validator<Product> {

    /**
     * Validates the fields of a Product object.
     *
     * @param product the Product to validate
     * @throws IllegalArgumentException if name is empty, price is negative, or stock is negative
     */
    @Override
    public void validate(Product product) {
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Product price cannot be negative.");
        }

        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty.");
        }

        if (product.getStock() < 0) {
            throw new IllegalArgumentException("Product stock cannot be negative.");
        }
    }
}

