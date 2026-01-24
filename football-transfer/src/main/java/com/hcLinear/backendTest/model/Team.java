package com.hcLinear.backendTest.model;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "teams")
public class Team {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @Column(nullable = false, unique = true)
	private String name;
	
    @Column(nullable = false)
	private String city;
	
	@Column(name = "founded_year", nullable= false)
	private Year foundedYear;
	
	@Column(nullable = false)
	private BigDecimal budget;
	
	@OneToMany(mappedBy = "team")
	private List<Player> player;

	public Team() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Year getFoundedYear() {
		return foundedYear;
	}

	public void setFoundedYear(Year foundedYear) {
		this.foundedYear = foundedYear;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getBudget() {
		return budget;
	}

	public void setBudget(BigDecimal budget) {
		this.budget = budget;
	}

	public List<Player> getPlayer() {
		return player;
	}

	public void setPlayer(List<Player> player) {
		this.player = player;
	}
	
	

}
