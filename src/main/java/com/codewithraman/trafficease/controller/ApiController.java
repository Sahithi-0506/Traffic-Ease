package com.codewithraman.trafficease.controller;

import com.codewithraman.trafficease.dto.TrafficSnapshotDto;
import com.codewithraman.trafficease.service.TrafficService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/traffic")
public class ApiController {

    @Autowired
    private TrafficService trafficService;

    @GetMapping("/snapshots")
    public List<TrafficSnapshotDto> getTrafficSnapshots() {
        return trafficService.getTrafficSnapshots();
    }
}
