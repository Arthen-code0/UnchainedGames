package modelos.unchainedgames.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import modelos.unchainedgames.models.Address;

@Data
@NoArgsConstructor
public class AddressCreateDTO {

    private Integer id;
    private Integer postalCode;
    private String street;
    private Integer number;
    private Integer floor;
    private String city;
    private String province;

    public AddressCreateDTO(Address address) {
        this.id = address.getId();
        this.postalCode = address.getPostalCode();
        this.street = address.getStreet();
        this.number = address.getNumber();
        this.floor = address.getFloor();
        this.city = address.getCity();
        this.province = address.getProvince();
    }
}
