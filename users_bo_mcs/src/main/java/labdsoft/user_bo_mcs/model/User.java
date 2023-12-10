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
    private Set<Vehicle> vehicles = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    private ParkyWallet parkies;

    @Enumerated
    private Role role;

    @Enumerated
    private PaymentMethod paymentMethod;

    @Enumerated
    private UserStatus status;

    public User(Name name, Email email, Password password, TaxIdNumber nif, Role role, Vehicle vehicle,
            PaymentMethod pMethod, UserStatus status) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.nif = nif;
        this.paymentMethod = pMethod;
        this.status = status;
        this.parkingHistory = new HashSet<>();
        this.vehicles = new HashSet<>();
        this.addVehicle(vehicle);
        this.parkies = new ParkyWallet(0);
        this.role = role;
    }

    public boolean addVehicle(Vehicle vehicle) {
        if (this.vehicles.size() >= 3) {
            return false;
        }
        return this.vehicles.add(vehicle);
    }

    public boolean addParkyTransactionEvent(ParkyTransactionEvent event) {
        return this.parkies.addEvent(event);
    }

    // missing Subscription (probably won't be necessary)
    public UserDTO toDto() {
        return new UserDTO(this.userId, this.name.getFirstName(), this.name.getLastName(), this.email.email(),
                this.getNif().number(), this.parkingHistory, this.parkies.parkies(), this.vehicles, this.role,
                this.paymentMethod, this.status);
    }

}
