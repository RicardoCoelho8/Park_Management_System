package labdsoft.user_bo_mcs.services;

import labdsoft.user_bo_mcs.model.Top10ParkyDTO;

import java.util.List;

public interface UserReportService {

    List<Top10ParkyDTO> getTop10Parky();
}
