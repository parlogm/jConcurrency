package ro.utm.jc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ro.utm.jc.model.entities.CenterNomenclature;
import ro.utm.jc.repo.CenterNomRepo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class CenterNomService {

    @Autowired
    private CenterNomRepo centerNomRepo;

    @Async("asyncExecutor")
    public CompletableFuture<List<CenterNomenclature>> findAllAsync() {
        log.info("Getting all centers from nomenclature asynchronously");
        return CompletableFuture.completedFuture(centerNomRepo.findAll());
    }

    public List<CenterNomenclature> findAll() {
        return centerNomRepo.findAll();
    }

    public List<CenterNomenclature> findAll(Pageable p) {
        return findAll(p);
    }

    public Page<CenterNomenclature> findAll(CenterNomenclature var, Pageable p) {
        return centerNomRepo.findAll(Example.of(var), p);
    }

    public Boolean existsByName(String name) {
        return centerNomRepo.existsByName(name);
    }

    public CenterNomenclature save(CenterNomenclature centerNomenclature) {
        return centerNomRepo.save(centerNomenclature);
    }

    public List<CenterNomenclature> saveAll(List<CenterNomenclature> centerNomenclatures) {
        return centerNomRepo.saveAll(centerNomenclatures);
    }

    public void deleteById(Long id) {
        centerNomRepo.deleteById(id);
    }

}
