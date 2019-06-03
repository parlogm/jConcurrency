package ro.utm.jc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ro.utm.jc.model.entities.CountryNomenclature;
import ro.utm.jc.repo.CountryNomRepo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class CountryNomService {

    @Autowired
    private CountryNomRepo countryNomRepo;

    @Async("asyncExecutor")
    public CompletableFuture<List<CountryNomenclature>> findAllAsync() {
        log.info("Getting all countries from nomenclature asynchronously");
        return CompletableFuture.completedFuture(countryNomRepo.findAll());
    }

    public List<CountryNomenclature> findAll() {
        return countryNomRepo.findAll();
    }

    public List<CountryNomenclature> findAll(Pageable p) {
        return findAll(p);
    }

    public Page<CountryNomenclature> findAll(CountryNomenclature var, Pageable p) {
        return countryNomRepo.findAll(Example.of(var), p);
    }

    public Boolean existsByCountryName(String countryName) {
        return countryNomRepo.existsByCountry(countryName);
    }

    public CountryNomenclature save(CountryNomenclature countryNomenclature) {
        return countryNomRepo.save(countryNomenclature);
    }

    public List<CountryNomenclature> saveAll(List<CountryNomenclature> countryNomenclatures) {
        return countryNomRepo.saveAll(countryNomenclatures);
    }

    public void deleteById(Long id) {
        countryNomRepo.deleteById(id);
    }

}
