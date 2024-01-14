package med.voll.api.domain.address;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String publicPlace;
    private String neighborhood;
    private String zipCode;
    private String city;
    private String uf;
    private String complement;
    private String addressNumber;

    public Address(AddressData address) {
        this.publicPlace = address.publicPlace();
        this.neighborhood =address.neighborhood();
        this.zipCode =address.zipCode();
        this.city = address.city();
        this.uf = address.uf();
        this.complement =address.complement();
        this.addressNumber = address.addressNumber();
    }

    public void updateInformation(AddressData data) {
        if (data.publicPlace() != null) {
            this.publicPlace = data.publicPlace();
        }
        if (data.neighborhood() != null) {
            this.neighborhood = data.neighborhood();
        }
        if (data.zipCode() != null) {
            this.zipCode = data.zipCode();
        }
        if (data.uf() != null) {
            this.uf = data.uf();
        }
        if (data.city() != null) {
            this.city = data.city();
        }
        if (data.addressNumber() != null) {
            this.addressNumber = data.addressNumber();
        }
        if (data.complement() != null) {
            this.complement = data.complement();
        }
    }

}
