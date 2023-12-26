package labdsoft.user_bo_mcs.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import labdsoft.user_bo_mcs.model.Top10ParkyDTO;
import labdsoft.user_bo_mcs.services.UserReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User report", description = "Endpoints for user reporting")
@RestController
@RequestMapping("/userReport")
public class UserReportController {

    @Autowired
    private UserReportService service;

    @Operation(summary = "gets top 10 users with most parkys")
    @GetMapping("/top10Parky")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Top10ParkyDTO>> getTop10Parky() {
        return new ResponseEntity<>(service.getTop10Parky(), HttpStatus.OK);
    }
}
