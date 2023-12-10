package labdsoft.user_bo_mcs.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ParkyWallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer parkies;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ParkyTransactionEvent> parkyEvents;

    public ParkyWallet(final Integer number) {
        Validate.notNull(number, "Parkies can't be null");
        Validate.isTrue(number >= 0);
        this.parkies = number;
        parkyEvents = new ArrayList<>();
    }

    public Integer parkies() {
        return this.parkies;
    }

    public boolean addEvent(ParkyTransactionEvent event) {
        if (this.parkies + event.getAmount() < 0) {
            return false;
        }

        Boolean addEvent = this.parkyEvents.add(event);
        if (addEvent) {
            this.parkies = this.parkies + event.getAmount();
        }
        return addEvent;
    }

}
