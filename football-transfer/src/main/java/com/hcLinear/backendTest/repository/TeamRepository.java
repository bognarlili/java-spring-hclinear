package com.hcLinear.backendTest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcLinear.backendTest.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
