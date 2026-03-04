package bll;

import model.Client;

public class ClientValidator implements Validator<Client> {

    /**
     * Validates the fields of a Client object.
     *
     * @param client the Client to validate
     * @throws IllegalArgumentException if the client's name or address is invalid
     */
    @Override
    public void validate(Client client) {
        if (client.getName() == null || client.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Client name cannot be empty.");
        }

        if (client.getAddress() == null || client.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Client address cannot be empty.");
        }
    }
}

