package com.codewithraman.trafficease.repository;

import com.codewithraman.trafficease.model.PoliceUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoliceUserRepository extends JpaRepository<PoliceUser, Long> {
    PoliceUser findByUsername(String username);
}
