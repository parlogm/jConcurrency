package ro.utm.jc.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ro.utm.jc.model.server.Server;

import java.util.List;

@Transactional
public interface ServerRepo extends JpaRepository<Server, Integer> {

    List<Server> findAll();
    Page<Server> findAll(Pageable p);
    Boolean existsByName(String name);

}
