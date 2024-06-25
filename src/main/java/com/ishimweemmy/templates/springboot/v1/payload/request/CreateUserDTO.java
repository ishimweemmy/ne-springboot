package com.ishimweemmy.templates.springboot.v1.payload.request;


import com.ishimweemmy.templates.springboot.v1.enums.EGender;
import com.ishimweemmy.templates.springboot.v1.enums.ERole;
import com.ishimweemmy.templates.springboot.v1.validators.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateUserDTO {

    @Email
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Pattern(regexp = "[0-9]{9,12}", message = "Your phone is not a valid tel we expect 2507***, or 07*** or 7***")
    private String telephone;

    private EGender gender;

    private ERole role;

    @ValidPassword
    private String password;
}
