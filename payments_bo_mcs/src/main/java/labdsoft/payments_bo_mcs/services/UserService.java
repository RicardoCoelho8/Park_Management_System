package labdsoft.payments_bo_mcs.services;


import labdsoft.payments_bo_mcs.model.payment.PaymentsDTO;
import labdsoft.payments_bo_mcs.model.user.AppUser;

public interface UserService {
    void createFromSubscribe(final AppUser user);

    void updateFromSubscribe(final AppUser user);
}
