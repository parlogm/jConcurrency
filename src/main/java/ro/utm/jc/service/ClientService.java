package ro.utm.jc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.utm.jc.model.entities.Client;
import ro.utm.jc.repo.ClientRepo;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepo clientRepo;

    public List<Client> findAll() {
        return clientRepo.findAll();
    }

    public List<Client> findAll(Pageable p) {
        return findAll(p);
    }

    public Page<Client> findAll(Client clientVar, Pageable p) {
        return clientRepo.findAll(Example.of(clientVar), p);
    }

    public Boolean existsByName(String name) {
        return clientRepo.existsByName(name);
    }

    public Boolean existsById(Long id) {
        return clientRepo.existsById(id.intValue());
    }

    public void save(Client client) {
        clientRepo.save(client);
    }

    public void deleteById(Long id) {
        clientRepo.deleteById(id.intValue());
    }

}
