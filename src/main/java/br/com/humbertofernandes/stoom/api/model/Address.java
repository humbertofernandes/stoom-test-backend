package br.com.humbertofernandes.stoom.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "endereco")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Address {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "address-1")
    @Column(name = "streetName")
    private String streetName;

    @NotBlank(message = "address-2")
    @Column(name = "number")
    private String number;

    @Column(name = "complement")
    private String complement;

    @NotBlank(message = "address-3")
    @Column(name = "neighbourhood")
    private String neighbourhood;

    @NotBlank(message = "address-4")
    @Column(name = "city")
    private String city;

    @NotBlank(message = "address-5")
    @Column(name = "state")
    private String state;

    @NotBlank(message = "address-6")
    @Column(name = "country")
    private String country;

    @NotBlank(message = "address-7")
    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;
}
