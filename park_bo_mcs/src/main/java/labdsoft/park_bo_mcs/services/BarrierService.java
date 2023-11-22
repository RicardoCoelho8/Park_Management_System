package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.dtos.park.BarrierLicenseReaderDTO;
import labdsoft.park_bo_mcs.dtos.park.BarrierDisplayDTO;

public interface BarrierService {
    BarrierDisplayDTO entranceOpticalReader(final BarrierLicenseReaderDTO barrierLicenseReaderDTO);

    BarrierDisplayDTO exitOpticalReader(final BarrierLicenseReaderDTO barrierLicenseReaderDTO);
}
