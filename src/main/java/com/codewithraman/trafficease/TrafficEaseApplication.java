package com.codewithraman.trafficease;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.codewithraman.trafficease.model.Junction;
import com.codewithraman.trafficease.model.PoliceUser;
import com.codewithraman.trafficease.repository.JunctionRepository;
import com.codewithraman.trafficease.repository.PoliceUserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TrafficEaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrafficEaseApplication.class, args);
    }

    // seed a default user + sample junctions
    @Bean
    CommandLineRunner seed(PoliceUserRepository userRepo, JunctionRepository junctionRepo, BCryptPasswordEncoder encoder) {
        return args -> {
            if (userRepo.findByUsername("police0") == null) {
                PoliceUser u = new PoliceUser();
                u.setUsername("police0");
                u.setPassword(encoder.encode("admin123")); // password admin123
                u.setRole("ROLE_POLICE");
                userRepo.save(u);
            }
            if (junctionRepo.count() == 0) {
                junctionRepo.save(new Junction("Junction 1"));
                junctionRepo.save(new Junction("Junction 2"));
                junctionRepo.save(new Junction("Junction 3"));
            } 
        };
    }
}
