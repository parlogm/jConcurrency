package ro.utm.jc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.utm.jc.model.entities.OrgNomenclature;
import ro.utm.jc.repo.OrgNomRepo;

import java.util.List;

@Service
public class OrgNomService {

    @Autowired
    private OrgNomRepo orgNomRepo;

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
