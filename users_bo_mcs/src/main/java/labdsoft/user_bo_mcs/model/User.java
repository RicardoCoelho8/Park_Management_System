package labdsoft.user_bo_mcs.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "AppUser")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long userId;

    @Embedded
    private Name name;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Embedded
    private TaxIdNumber nif;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<ParkingHistory> parkingHistory;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Vehicle> vehicles;

    @Column(nullable = false)
    private String accountNumber;

    @Embedded
    private ParkyWallet parkies;


    public User(Name name, Email email, Password password, String accountNumber, TaxIdNumber nif) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.accountNumber = accountNumber;
        this.nif = nif;
        this.parkingHistory = new HashSet<>();
        this.vehicles = new HashSet<>();
        this.parkies = new ParkyWallet(0);
    }

    public boolean addVehicle(Vehicle vehicle) {
        if (this.vehicles.size() >= 3) {
            return false;
        }
        return this.vehicles.add(vehicle);
    }

    // missing  Subscription (probably won't be necessary)
    public UserDTO toDto() {
        return new UserDTO(this.userId, this.name.getFirstName(), this.name.getLastName(), this.email.email(), this.accountNumber, this.getNif().number(), this.parkingHistory, this.parkies.parkies(), this.vehicles);
    }

}
