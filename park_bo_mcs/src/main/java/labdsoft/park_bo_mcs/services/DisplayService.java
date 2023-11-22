package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.dtos.park.BarrierDisplayDTO;
import labdsoft.park_bo_mcs.dtos.park.DisplayDTO;
import labdsoft.park_bo_mcs.dtos.park.DisplayGetDTO;
import labdsoft.park_bo_mcs.dtos.park.DisplayUpdateDTO;

public interface DisplayService {
    BarrierDisplayDTO updateDisplayMessage(final DisplayUpdateDTO barrierLicenseReaderDTO);

    DisplayDTO getDisplayMessage(DisplayGetDTO displayGetDTO);
}
