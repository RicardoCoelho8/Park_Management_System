package labdsoft.user_bo_mcs.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Vehicle {

    @Id
    private String licensePlateNumber;

    @Enumerated
    private VehicleType vehicleType;



    public Vehicle(String licensePlateNumber, VehicleType vehicleType) {
        Pattern p = Pattern.compile("^(([A-Z]{2}-\\d{2}-(\\d{2}|[A-Z]{2}))|(\\d{2}-(\\d{2}-[A-Z]{2}|[A-Z]{2}-\\d{2})))$");
        Matcher m = p.matcher(licensePlateNumber);
        if (!m.matches()) {
            throw new IllegalArgumentException("Invalid license plate number!");
        }
        this.licensePlateNumber = licensePlateNumber;
        this.vehicleType = vehicleType;
    }

}
