package com.hcLinear.backendTest.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

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

	@OneToOne
	@JoinColumn(name = "captain_id")
	private Player captain;


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

	public Player getCaptain() {
		return captain;
	}

	public void setCaptain(Player captain) {
		this.captain = captain;
	}
}
