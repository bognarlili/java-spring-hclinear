package com.hcLinear.backendTest.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "players")
public class Player {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @Column(name = "first_name", nullable= false)
	private String firstName;
	
    @Column(name = "last_name", nullable= false)
	private String lastName;
	
    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable= false)
	private PlayerPosition playerPosition;
	
    @Column(name = "shirt_number", nullable= false)
	private Integer shirtNumber;
	
    @Column(name = "birth_date", nullable= false)
	private LocalDate birthDate;
    
    @Column(name = "market_value", nullable= false)
	private BigDecimal marketValue;
    
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

	public Player() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public PlayerPosition getPlayerPosition() {
		return playerPosition;
	}

	public void setPlayerPosition(PlayerPosition playerPosition) {
		this.playerPosition = playerPosition;
	}

	public Integer getShirtNumber() {
		return shirtNumber;
	}

	public void setShirtNumber(Integer shirtNumber) {
		this.shirtNumber = shirtNumber;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public BigDecimal getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(BigDecimal marketValue) {
		this.marketValue = marketValue;
	}
	
	

}
