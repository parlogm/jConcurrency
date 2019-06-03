package ro.utm.jc.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ro.utm.jc.model.entities.CountryNomenclature;

import java.util.List;

@Transactional
public interface CountryNomRepo extends JpaRepository<CountryNomenclature, Long> {

    List<CountryNomenclature> findAll();
    Page<CountryNomenclature> findAll(Pageable p);
    Boolean existsByCountry(String country);

}
