package com.tollplaza.TollPlaza.entity;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="operator")
public class Operators {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long opId;
	    private String name;
	    private String booth;
	    private String email;
	    private String password;

//	    @Column(name = "date")
//	    private LocalDate date;


	    @OneToMany(mappedBy = "operators", cascade = CascadeType.ALL)
	    @JsonIgnore
	    private List<Booth> booths;
	    
	    @OneToMany(mappedBy = "operator", cascade = CascadeType.ALL)
	    @JsonManagedReference
	    private List<LoginDetails> loginDetails;
	    

		public List<LoginDetails> getLoginDetails() {
			return loginDetails;
		}

		public void setLoginDetails(List<LoginDetails> loginDetails) {
			this.loginDetails = loginDetails;
		}

		public Long getOpId() {
			return opId;
		}

		public void setOpId(Long opId) {
			this.opId = opId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getBooth() {
			return booth;
		}

		public void setBooth(String booth) {
			this.booth = booth;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

//		public LocalDate getDate() {
//			return date;
//		}
//
//		public void setDate(LocalDate date) {
//			this.date = date;
//		}

		public List<Booth> getBooths() {
			return booths;
		}

		public void setBooths(List<Booth> booths) {
			this.booths = booths;
		}
	    
	    
}