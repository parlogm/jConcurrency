package ro.utm.jc.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ro.utm.jc.model.entities.Client;

import java.util.List;

@Transactional
public interface ClientRepo extends JpaRepository<Client, Long> {

    List<Client> findAll();
    Page<Client> findAll(Pageable p);
    Boolean existsByName(String name);

}
