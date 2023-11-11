package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.dtos.BarrierLicenseReaderDTO;
import labdsoft.park_bo_mcs.dtos.EntranceBarrierDTO;
import labdsoft.park_bo_mcs.dtos.ExitBarrierDTO;

public interface BarrierService {
    EntranceBarrierDTO entranceOpticalReader(final BarrierLicenseReaderDTO barrierLicenseReaderDTO);

    ExitBarrierDTO exitOpticalReader(final BarrierLicenseReaderDTO barrierLicenseReaderDTO);
}
