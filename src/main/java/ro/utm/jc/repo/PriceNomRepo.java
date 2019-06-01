package ro.utm.jc.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ro.utm.jc.model.entities.PriceNomenclature;

import java.util.List;

@Transactional
public interface PriceNomRepo extends JpaRepository<PriceNomenclature, Long> {

    List<PriceNomenclature> findAll();
    Page<PriceNomenclature> findAll(Pageable p);
    Boolean existsByGroupName (String groupName);

}
