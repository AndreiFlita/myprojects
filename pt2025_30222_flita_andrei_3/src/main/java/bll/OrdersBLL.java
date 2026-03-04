package bll;

import dao.OrdersDAO;
import model.Orders;

import java.util.List;
import java.util.NoSuchElementException;

public class OrdersBLL {

    private final OrdersValidator validator;
    private final OrdersDAO orderDAO;

    public OrdersBLL() {
        validator = new OrdersValidator();
        orderDAO = new OrdersDAO();
    }
    /**
     * Retrieves an order by its unique ID.
     *
     * @param id the ID of the order to find
     * @return the Orders object with the specified ID
     * @throws NoSuchElementException if no order with the given ID is found
     */
    public Orders findOrderById(int id) {
        Orders order = orderDAO.findById(id);
        if (order == null) {
            throw new NoSuchElementException("The order with id = " + id + " was not found!");
        }
        return order;
    }
    /**
     * Retrieves all orders from the database.
     *
     * @return a list of all orders
     */
    public List<Orders> findAllOrders() {
        return orderDAO.findAll();
    }
    /**
     * Inserts a new order into the database after validation.
     *
     * @param order the Orders object to insert
     * @throws IllegalArgumentException if validation fails
     */
    public void insertOrder(Orders order) {
        validator.validate(order);
        orderDAO.insert(order);
    }
    /**
     * Updates an existing order in the database after validation.
     *
     * @param order the Orders object to update
     * @throws IllegalArgumentException if validation fails
     */
    public void updateOrder(Orders order) {
        validator.validate(order);
        orderDAO.update(order);
    }
    /**
     * Deletes an order by ID.
     *
     * @param id the ID of the order to delete
     */
    public void deleteOrder(int id) {
        orderDAO.delete(id);
    }
}

