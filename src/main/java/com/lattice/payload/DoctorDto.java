package com.lattice.payload;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class DoctorDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	@NotBlank(message = "should be at least 3 characters")
	@Size(min = 3,max = 20, message="should be at least 3 characters")
	private String name;
	
	
	@NotBlank(message="Please enter the city Blank")
	@NotNull(message="Please enter the city")
	@Size(min = 3,max=20,message="should be at max 20 characters")
	private String city;
	
	
	@NotBlank(message="should be a valid email address")
	@Email(message="Invalid Email")
	private String email;
	
	
	@NotBlank(message="Phone number should be at least 10 number")
	@Size(min=10,message="should be at least 10 number")
	private String phoneNumber;
	
	
	@NotBlank(message="Please enter the Speciality")
	@NotNull(message="Please enter the Speciality")
	private String speciality;
	
}
