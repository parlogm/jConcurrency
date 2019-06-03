package ro.utm.jc.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ro.utm.jc.model.entities.CenterNomenclature;

import java.util.List;

@Transactional
public interface CenterNomRepo extends JpaRepository<CenterNomenclature, Long> {

    List<CenterNomenclature> findAll();
    Page<CenterNomenclature> findAll(Pageable p);
    Boolean existsByName(String name);

}
