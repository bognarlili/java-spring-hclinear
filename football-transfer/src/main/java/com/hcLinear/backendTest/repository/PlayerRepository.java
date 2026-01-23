package com.hcLinear.backendTest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcLinear.backendTest.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {

}
