package labdsoft.user_bo_mcs.model;



import org.apache.commons.lang3.Validate;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class TaxIdNumber {

    private Integer nif;

    public TaxIdNumber(final Integer number) {
        Validate.notNull(number, "Tax id number can't be null");
        Validate.isTrue(number > 100000000 && number < 999999999, "Invalid tax id number");
        this.nif = number;
    }

    public Integer number() {
        return this.nif;
    }



}
