package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.dtos.BarrierDisplayDTO;
import labdsoft.park_bo_mcs.dtos.DisplayDTO;
import labdsoft.park_bo_mcs.dtos.DisplayGetDTO;
import labdsoft.park_bo_mcs.dtos.DisplayUpdateDTO;

public interface DisplayService {
    BarrierDisplayDTO updateDisplayMessage(final DisplayUpdateDTO barrierLicenseReaderDTO);

    DisplayDTO getDisplayMessage(DisplayGetDTO displayGetDTO);
}
