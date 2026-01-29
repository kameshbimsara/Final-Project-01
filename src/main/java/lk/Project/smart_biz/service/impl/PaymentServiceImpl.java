package lk.Project.smart_biz.service.impl;

import lk.Project.smart_biz.dto.PaymentDto;
import lk.Project.smart_biz.entity.Business;
import lk.Project.smart_biz.entity.Payments;
import lk.Project.smart_biz.repo.PaymentRepo;
import lk.Project.smart_biz.service.PaymentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepo paymentRepo;

    public PaymentServiceImpl(PaymentRepo paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    @Override
    public PaymentDto savePayment(PaymentDto paymentDto) {
        Payments payments = new Payments();
        payments.setId(paymentDto.getId());
        payments.setDate(LocalDate.now());
        payments.setPrice(paymentDto.getPrice());
        payments.setBusiness(new Business(paymentDto.getBusinessId()));

        Payments saved = paymentRepo.save(payments);

        return new PaymentDto(saved.getId(), saved.getDate(), saved.getPrice(), saved.getBusiness().getId());
    }

    @Override
    public PaymentDto getPaymentById(Integer id) {

        Optional<Payments> byId = paymentRepo.findById(id);
        if (byId.isPresent()) {
            Payments payments = byId.get();
            return new PaymentDto(id, payments.getDate(), payments.getPrice(), payments.getBusiness().getId());
        }
        return null;
    }

    @Override
    public List<PaymentDto> getAllPayment() {
        List<Payments> all = paymentRepo.findAll();
        return all.stream()
                .map(payments -> new PaymentDto(payments.getId(),payments.getDate(),payments.getPrice(),payments.getBusiness().getId()))
                .toList();
    }

    @Override
    public List<PaymentDto> getPaymentByBusinessId(Integer businessId) {

        List<Payments> payments = paymentRepo.findByBusiness_Id(businessId);

        if (payments.isEmpty()) {
            throw new RuntimeException("No payments found for this business");
        }

        return payments.stream()
                .map(payment -> new PaymentDto(
                        payment.getId(),
                        payment.getDate(),
                        payment.getPrice(),
                        payment.getBusiness().getId()
                ))
                .toList();
    }
}
