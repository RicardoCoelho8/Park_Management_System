package labdsoft.payments_bo_mcs.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import labdsoft.payments_bo_mcs.model.barrier.BarrierInfoDTO;
import labdsoft.payments_bo_mcs.model.payment.PaymentsDTO;
import labdsoft.payments_bo_mcs.services.PaymentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;


@Tag(name = "Payments", description = "Endpoints for managing payments")
@RestController
@RequestMapping("/payments")
class PaymentsController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentsController.class);

    @Autowired
    private PaymentsService service;

    @Operation(summary = "create payment from information from of the barrier")
    @PostMapping
    public ResponseEntity<PaymentsDTO> createPayment(@RequestBody final BarrierInfoDTO barrierInfoDTO) throws Exception {
        return ResponseEntity.ok().body(service.createFromBarrier(barrierInfoDTO));
    }

    @Operation(summary = "gets catalog, i.e. all payments")
    @GetMapping
    public ResponseEntity<Iterable<PaymentsDTO>> getCatalog() {
        return ResponseEntity.ok().body(service.getCatalog());
    }

    @Operation(summary = "finds payments by nif")
    @GetMapping(value = "/designation/{nif}")
    public ResponseEntity<Iterable<PaymentsDTO>> findAllByDesignation(@PathVariable("nif") final String nif) {
        return ResponseEntity.ok().body(service.findByUserNIF(nif));
    }
}