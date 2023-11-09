package labdsoft.payments_bo_mcs.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import labdsoft.payments_bo_mcs.communication.Subscribe;
import labdsoft.payments_bo_mcs.model.Payments;
import labdsoft.payments_bo_mcs.model.PaymentsDTO;
import labdsoft.payments_bo_mcs.services.PaymentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Tag(name = "Product", description = "Endpoints for managing  products")
@RestController
@RequestMapping("/products")
class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private Subscribe subscribe;

    @Autowired
    private PaymentsService service;

    @Operation(summary = "creates a product")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PaymentsDTO> create(@RequestBody Payments manager) {
        try {
            final PaymentsDTO product = service.create(manager);
            return new ResponseEntity<PaymentsDTO>(product, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product must have a unique SKU.");
        }
    }

    @Operation(summary = "gets catalog, i.e. all products")
    @GetMapping
    public ResponseEntity<Iterable<List<PaymentsDTO>>> getCatalog() {
        final var payments = service. ();

        return ResponseEntity.ok().body(payments);
    }

    @Operation(summary = "finds payments by nif")
    @GetMapping(value = "/designation/{designation}")
    public ResponseEntity<Iterable<PaymentsDTO>> findAllByDesignation(@PathVariable("designation") final String nif) {

        final Iterable<PaymentsDTO> products = service.findByUserNIF(nif);

        return ResponseEntity.ok().body(products);
    }
}