package bll;

import model.Orders;

public class OrdersValidator implements Validator<Orders> {

    /**
     * Validates the fields of an Orders object.
     *
     * @param order the Orders object to validate
     * @throws IllegalArgumentException if client ID, product ID, or quantity is invalid
     */
    @Override
    public void validate(Orders order) {
        if (order.getClient_id() <= 0) {
            throw new IllegalArgumentException("Client ID must be a positive number.");
        }

        if (order.getProduct_id() <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number.");
        }

        if (order.getQuantity() <= 0) {
            throw new IllegalArgumentException("Order quantity must be greater than zero.");
        }
    }
}

