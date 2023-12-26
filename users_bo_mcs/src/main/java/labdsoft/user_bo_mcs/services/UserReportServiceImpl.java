package labdsoft.user_bo_mcs.services;

import labdsoft.user_bo_mcs.model.Top10ParkyDTO;
import labdsoft.user_bo_mcs.model.User;
import labdsoft.user_bo_mcs.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserReportServiceImpl implements UserReportService{

    @Autowired
    private UserRepository repository;

    @Override
    public List<Top10ParkyDTO> getTop10Parky() {
        List<User> users = (List<User>) repository.findAll();

        if (users.isEmpty()) {
            throw new RuntimeException("No users found");
        }

        //Get top 10 users with most parkys
        return users.stream()
                .sorted((u1, u2) -> u2.getParkies().getParkies().compareTo(u1.getParkies().getParkies()))
                .map(u -> new Top10ParkyDTO(u.getName().toString(), u.getEmail().email(), u.getParkies().getParkies()))
                .limit(10)
                .toList();
    }
}
