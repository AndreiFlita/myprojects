package bll;

import dao.ProductDAO;
import model.Product;

import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {

    private final ProductValidator validator;
    private final ProductDAO productDAO;

    public ProductBLL() {
        validator = new ProductValidator();
        productDAO = new ProductDAO();
    }
    /**
     * Retrieves a product by its unique ID.
     *
     * @param id the ID of the product to find
     * @return the Product object with the specified ID
     * @throws NoSuchElementException if no product with the given ID is found
     */
    public Product findProductById(int id) {
        Product product = productDAO.findById(id);
        if (product == null) {
            throw new NoSuchElementException("The product with id = " + id + " was not found!");
        }
        return product;
    }
    /**
     * Retrieves all products from the database.
     *
     * @return a list of all products
     */
    public List<Product> findAllProducts() {
        return productDAO.findAll();
    }
    /**
     * Inserts a new product into the database after validation.
     *
     * @param product the Product object to insert
     * @throws IllegalArgumentException if validation fails
     */
    public void insertProduct(Product product) {
        validator.validate(product);
        productDAO.insert(product);
    }
    /**
     * Updates an existing product in the database after validation.
     *
     * @param product the Product object to update
     * @throws IllegalArgumentException if validation fails
     */
    public void updateProduct(Product product) {
        validator.validate(product);
        productDAO.update(product);
    }
    /**
     * Deletes a product by ID.
     *
     * @param id the ID of the product to delete
     */
    public void deleteProduct(int id) {
        productDAO.delete(id);
    }
}

