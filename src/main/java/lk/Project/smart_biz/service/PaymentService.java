package lk.Project.smart_biz.service;

import lk.Project.smart_biz.dto.PaymentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentService {
    PaymentDto savePayment(PaymentDto paymentDto);
    PaymentDto getPaymentById(Integer id);
    List<PaymentDto> getAllPayment();
    List<PaymentDto> getPaymentByBusinessId(Integer businessId);
}
