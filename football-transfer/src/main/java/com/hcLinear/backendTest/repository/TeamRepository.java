package com.hcLinear.backendTest.repository;

import com.hcLinear.backendTest.dto.team.TeamListResponse;
import com.hcLinear.backendTest.dto.team.TeamResponse;
import com.hcLinear.backendTest.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hcLinear.backendTest.model.Team;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {


    boolean existsByName(String name);

    @Query("""
      select distinct t
      from Team t
      left join fetch t.player
      left join fetch t.captain
    """)
    List<Team> findAllWithPlayersAndCaptain();






}
