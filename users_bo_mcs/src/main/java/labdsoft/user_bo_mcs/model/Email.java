package labdsoft.user_bo_mcs.model;

import org.apache.commons.lang3.Validate;
import org.apache.commons.validator.routines.EmailValidator;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Email {
    
    @Column(length = 40, unique = true)
    private String email;

    public Email(final String email) {
        Validate.isTrue(EmailValidator.getInstance().isValid(email), "Invalid email");
        this.email = email;
    }

    public String email() {
        return this.email;
    }

}
