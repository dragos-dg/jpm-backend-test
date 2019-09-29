package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Data
public class UserProfileModel {
    Integer id;
    String name;
    String username;
    String email;
    Address address;
    Company company;
    String phone;
    String website;

    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Data
    @Builder
    public static class Address {
        String street;
        String suite;
        String city;
        String zipcode;
        Geo geo;
    }

    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Data
    @Builder
    public static class Geo {
        String lat;
        String lng;
    }

    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Data
    @Builder
    public static class Company {
        String name;
        String catchPhrase;
        String bs;
    }

}
