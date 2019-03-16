package ro.utm.jc.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ro.utm.jc.model.entities.FidelityNomenclature;

import java.util.List;

@Transactional
public interface FidelityNomRepo extends JpaRepository<FidelityNomenclature, Long> {

    List<FidelityNomenclature> findAll();
    Page<FidelityNomenclature> findAll(Pageable p);
    Boolean existsByGroupName(String groupName);

}
