package bll;

import dao.ClientDAO;
import model.Client;

import java.util.List;
import java.util.NoSuchElementException;

public class ClientBLL {

    private final ClientValidator validator;
    private final ClientDAO clientDAO;

    public ClientBLL() {
        validator = new ClientValidator();
        clientDAO = new ClientDAO();
    }
    /**
     * Retrieves a client by their unique ID.
     *
     * @param id the ID of the client to find
     * @return the Client object with the specified ID
     * @throws NoSuchElementException if no client with the given ID is found
     */
    public Client findClientById(int id) {
        Client client = clientDAO.findById(id);
        if (client == null) {
            throw new NoSuchElementException("The client with id = " + id + " was not found!");
        }
        return client;
    }
    /**
     * Retrieves a list of all clients from the database.
     *
     * @return a List of {@link Client} objects
     */
    public List<Client> findAllClients() {
        return clientDAO.findAll();
    }
    /**
     * Inserts a new client into the database after validation.
     *
     * @param client the {@link Client} object to insert
     * @throws IllegalArgumentException if client data is invalid
     */
    public void insertClient(Client client) {
        validator.validate(client);
        clientDAO.insert(client);
    }
    /**
     * Updates an existing client in the database after validation.
     *
     * @param client the {@link Client} object with updated data
     * @throws IllegalArgumentException if client data is invalid
     */
    public void updateClient(Client client) {
        validator.validate(client);
        clientDAO.update(client);
    }
    /**
     * Deletes a client from the database by their ID.
     *
     * @param id the ID of the client to delete
     */
    public void deleteClient(int id) {
        clientDAO.delete(id);
    }
}

