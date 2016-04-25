package com.qqj.org.repository;

import com.qqj.org.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TeamRepository extends JpaRepository<Team, Long> , JpaSpecificationExecutor<Team>{
}
