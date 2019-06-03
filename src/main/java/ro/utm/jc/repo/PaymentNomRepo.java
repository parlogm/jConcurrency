package ro.utm.jc.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ro.utm.jc.model.entities.PaymentNomenclature;

import java.util.List;

@Transactional
public interface PaymentNomRepo extends JpaRepository<PaymentNomenclature, Long> {

    List<PaymentNomenclature> findAll();
    Page<PaymentNomenclature> findAll(Pageable p);
    Boolean existsByMethodName (String methodName);

}
