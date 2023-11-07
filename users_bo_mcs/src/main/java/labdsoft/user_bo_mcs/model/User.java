package labdsoft.user_bo_mcs.model;

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

    @Column(nullable = false)
    private String accountNumber;


    public User(Name name, Email email, Password password, String accountNumber, TaxIdNumber nif) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.accountNumber = accountNumber;
        this.nif = nif;
    }

    // missing subscription (probably won't be necessary), parking history, vehicle
    public UserDTO toDto() {
        return new UserDTO(this.userId, this.name.getFirstName(), this.name.getLastName(), this.email.email(), this.accountNumber, this.getNif().number());
    }

}
