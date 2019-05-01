package ro.utm.jc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.utm.jc.model.entities.FidelityNomenclature;
import ro.utm.jc.repo.FidelityNomRepo;

import java.util.List;

@Service
public class FidelityNomService {

    @Autowired
    private FidelityNomRepo fidelityNomRepo;

    public List<FidelityNomenclature> findAll() {
        return fidelityNomRepo.findAll();
    }

    public List<FidelityNomenclature> findAll(Pageable p) {
        return findAll(p);
    }

    public Page<FidelityNomenclature> findAll(FidelityNomenclature var, Pageable p) {
        return fidelityNomRepo.findAll(Example.of(var), p);
    }

    public Boolean existsByGroupName(String groupName) {
        return fidelityNomRepo.existsByGroupName(groupName);
    }

    public FidelityNomenclature save(FidelityNomenclature fidelityNomenclature) {
        return fidelityNomRepo.save(fidelityNomenclature);
    }

    public List<FidelityNomenclature> saveAll(List<FidelityNomenclature> fidelityNomenclatureList) {
        return fidelityNomRepo.saveAll(fidelityNomenclatureList);
    }

    public void deleteById(Long id) {
        fidelityNomRepo.deleteById(id);
    }

}
