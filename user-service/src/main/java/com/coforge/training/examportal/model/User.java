package com.coforge.training.examportal.model;


import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String firstname;
	
	private String lastname;
	
	private String password;
	
	@Column(unique = true)
	private String email;
	
	@Column(nullable = false, unique = true)
	private String mobile;
	
	private String city;
	
	private Date dob;
	
	private String qualification;
	
	private String yoc;


}
