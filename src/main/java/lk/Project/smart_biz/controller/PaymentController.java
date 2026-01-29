package lk.Project.smart_biz.controller;

import lk.Project.smart_biz.dto.PaymentDto;
import lk.Project.smart_biz.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
@CrossOrigin
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public PaymentDto savePayment(@RequestBody PaymentDto paymentDto) {
        return paymentService.savePayment(paymentDto);
    }

    @GetMapping("/{id}")
    public PaymentDto getPaymentById(@PathVariable("id") Integer id) {
        return paymentService.getPaymentById(id);
    }

    @GetMapping
    public List<PaymentDto> getAllPayment() {
        return paymentService.getAllPayment();
    }

    @GetMapping("/business/{businessId}")
    public List<PaymentDto> getPaymentByBusinessId(@PathVariable Integer businessId) {
        return paymentService.getPaymentByBusinessId(businessId);
    }

}
