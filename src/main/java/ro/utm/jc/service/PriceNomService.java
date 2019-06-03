package ro.utm.jc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.utm.jc.model.entities.PriceNomenclature;
import ro.utm.jc.repo.PriceNomRepo;

import java.util.List;

@Service
public class PriceNomService {

    @Autowired
    private PriceNomRepo priceNomRepo;

    public List<PriceNomenclature> findAll() {
        return priceNomRepo.findAll();
    }

    public List<PriceNomenclature> findAll(Pageable p) {
        return findAll(p);
    }

    public Page<PriceNomenclature> findAll(PriceNomenclature var, Pageable p) {
        return priceNomRepo.findAll(Example.of(var), p);
    }

    public Boolean existsByGroupName(String groupName) {
        return priceNomRepo.existsByGroupName(groupName);
    }

    public PriceNomenclature save(PriceNomenclature priceNomenclature) {
        return priceNomRepo.save(priceNomenclature);
    }

    public List<PriceNomenclature> saveAll(List<PriceNomenclature> priceNomenclatures) {
        return priceNomRepo.saveAll(priceNomenclatures);
    }

    public void deleteById(Long id) {
        priceNomRepo.deleteById(id);
    }

}
