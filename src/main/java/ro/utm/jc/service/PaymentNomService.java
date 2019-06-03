package ro.utm.jc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.utm.jc.model.entities.PaymentNomenclature;
import ro.utm.jc.repo.PaymentNomRepo;

import java.util.List;

@Service
public class PaymentNomService {

    @Autowired
    private PaymentNomRepo paymentNomRepo;

    public List<PaymentNomenclature> findAll() {
        return paymentNomRepo.findAll();
    }

    public List<PaymentNomenclature> findAll(Pageable p) {
        return findAll(p);
    }

    public Page<PaymentNomenclature> findAll(PaymentNomenclature var, Pageable p) {
        return paymentNomRepo.findAll(Example.of(var), p);
    }

    public Boolean existsByMethodName(String methodName) {
        return paymentNomRepo.existsByMethodName(methodName);
    }

    public PaymentNomenclature save(PaymentNomenclature paymentNomenclature) {
        return paymentNomRepo.save(paymentNomenclature);
    }

    public List<PaymentNomenclature> saveAll(List<PaymentNomenclature> paymentNomenclatures) {
        return paymentNomRepo.saveAll(paymentNomenclatures);
    }

    public void deleteById(Long id) {
        paymentNomRepo.deleteById(id);
    }

}
