package ro.utm.jc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ro.utm.jc.model.entities.OrgNomenclature;
import ro.utm.jc.repo.OrgNomRepo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class OrgNomService {

    @Autowired
    private OrgNomRepo orgNomRepo;

    @Async("asyncExecutor")
    public CompletableFuture<List<OrgNomenclature>> findAllAsync() {
        log.info("Getting all org types from nomenclature asynchronously");
        return CompletableFuture.completedFuture(orgNomRepo.findAll());
    }

    public List<OrgNomenclature> findAll() {
        return orgNomRepo.findAll();
    }

    public List<OrgNomenclature> findAll(Pageable p) {
        return findAll(p);
    }

    public Page<OrgNomenclature> findAll(OrgNomenclature var, Pageable p) {
        return orgNomRepo.findAll(Example.of(var), p);
    }

    public Boolean existsByType(String type) {
        return orgNomRepo.existsByType(type);
    }

    public OrgNomenclature save(OrgNomenclature orgNomenclature) {
        return orgNomRepo.save(orgNomenclature);
    }

    public List<OrgNomenclature> saveAll(List<OrgNomenclature> orgNomenclatures) {
        return orgNomRepo.saveAll(orgNomenclatures);
    }

    public void deleteById(Long id) {
        orgNomRepo.deleteById(id);
    }

}
