package ro.utm.jc.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ro.utm.jc.model.entities.OrgNomenclature;

import java.util.List;

@Transactional
public interface OrgNomRepo extends JpaRepository<OrgNomenclature, Long> {

    List<OrgNomenclature> findAll();
    Page<OrgNomenclature> findAll(Pageable p);
    Boolean existsByType (String type);

}
