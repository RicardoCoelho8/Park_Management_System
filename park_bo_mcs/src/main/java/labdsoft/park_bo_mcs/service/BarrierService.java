package labdsoft.park_bo_mcs.service;

import labdsoft.park_bo_mcs.dto.BarrierLicenseReaderDTO;
import labdsoft.park_bo_mcs.dto.EntranceBarrierDTO;
import labdsoft.park_bo_mcs.dto.ExitBarrierDTO;

public interface BarrierService {
    EntranceBarrierDTO entranceOpticalReader(final BarrierLicenseReaderDTO barrierLicenseReaderDTO);

    ExitBarrierDTO exitOpticalReader(final BarrierLicenseReaderDTO barrierLicenseReaderDTO);
}
