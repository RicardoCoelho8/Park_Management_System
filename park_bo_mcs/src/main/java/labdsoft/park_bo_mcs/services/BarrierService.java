package labdsoft.park_bo_mcs.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import labdsoft.park_bo_mcs.dtos.BarrierLicenseReaderDTO;
import labdsoft.park_bo_mcs.dtos.BarrierDisplayDTO;

public interface BarrierService {
    BarrierDisplayDTO entranceOpticalReader(final BarrierLicenseReaderDTO barrierLicenseReaderDTO) throws Exception;

    BarrierDisplayDTO exitOpticalReader(final BarrierLicenseReaderDTO barrierLicenseReaderDTO) throws Exception;
}
