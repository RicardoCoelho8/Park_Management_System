package labdsoft.user_bo_mcs.model;

import org.apache.commons.lang3.Validate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class ParkyWallet {

    @Column
    private Integer parkies;

    public ParkyWallet(final Integer number) {
        Validate.notNull(number, "Parkies can't be null");
        Validate.isTrue(number >= 0 );
        this.parkies = number;
    }

    public Integer parkies() {
        return this.parkies;
    }



}
