package com.codewithraman.trafficease.service;

import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.Random;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.codewithraman.trafficease.model.Junction;
import com.codewithraman.trafficease.model.TrafficData;
import com.codewithraman.trafficease.repository.JunctionRepository;
import com.codewithraman.trafficease.repository.TrafficDataRepository;
import com.codewithraman.trafficease.dto.JunctionDto;
import com.codewithraman.trafficease.dto.TrafficSnapshotDto;

import org.springframework.transaction.annotation.Transactional;

@Service
public class TrafficService {

    private final JunctionRepository junctionRepo;
    private final TrafficDataRepository dataRepo;
    private final AlertService alertService;
    private final Random random = new Random();

    // threshold for heavy congestion -> triggers alert
    private final int CONGESTION_THRESHOLD = 150;
    private final String ALERT_EMAIL_TO = "achyutha0506@gmail.com"; // change if you want to receive emails

    public TrafficService(JunctionRepository junctionRepo, TrafficDataRepository dataRepo, AlertService alertService) {
        this.junctionRepo = junctionRepo;
        this.dataRepo = dataRepo;
        this.alertService = alertService;
    }

    // scheduled simulator: runs every 5 seconds
    @Scheduled(fixedRate = 5000)
    public void scheduledSimulate() {
        simulateTrafficAndOptimize();
    }

    @Transactional
    public void simulateTrafficAndOptimize() {
        List<Junction> junctions = junctionRepo.findAll();
        for (Junction j : junctions) {
            int north = random.nextInt(80);
            int east  = random.nextInt(80);
            int south = random.nextInt(80);
            int west  = random.nextInt(80);

            TrafficData td = new TrafficData();
            td.setJunction(j);
            td.setNorthLane(north);
            td.setEastLane(east);
            td.setSouthLane(south);
            td.setWestLane(west);
            td.setTotalQueue(north + east + south + west);
            td.setTimestamp(LocalDateTime.now());

            dataRepo.save(td);
            optimizeSignals(j, td);
        }
    }

    @Transactional
    public void optimizeSignals(Junction j, TrafficData d) {
        int north = d.getNorthLane();
        int east  = d.getEastLane();
        int south = d.getSouthLane();
        int west  = d.getWestLane();
        int total = north + east + south + west;
        if (total == 0) total = 1;

        int baseCycle = 60;
        int nsShare = ((north + south) * baseCycle) / total;
        int ewShare = baseCycle - nsShare;

        // if west overloaded -> boost EW
        if (west > (total * 0.5)) {
            ewShare = Math.min(50, ewShare + 10);
            nsShare = baseCycle - ewShare;
        }

        nsShare = Math.max(10, Math.min(50, nsShare));
        ewShare = Math.max(10, Math.min(50, ewShare));
        j.setNsSignal(nsShare);
        j.setEwSignal(ewShare);

        // handle alerts
        if (d.getTotalQueue() > CONGESTION_THRESHOLD) {
            j.setCongestionAlert(true);
            String msg = "High congestion at " + j.getName() + " (total: " + d.getTotalQueue() + ")";
            alertService.sendSms(msg);
            alertService.sendEmail("Traffic Alert - " + j.getName(), msg, ALERT_EMAIL_TO);
        } else {
            j.setCongestionAlert(false);
        }
        junctionRepo.save(j);
    }

    // ✅ Existing: Get Junction details
    public List<JunctionDto> getAllJunctionsDto() {
        return junctionRepo.findAll().stream()
                .map(j -> new JunctionDto(j.getId(), j.getName(), j.getNsSignal(), j.getEwSignal(), j.isCongestionAlert()))
                .collect(Collectors.toList());
    }

    // ✅ Existing: Get recent data for one junction
    public List<TrafficSnapshotDto> getRecentTrafficForJunction(Long junctionId) {
        Junction j = junctionRepo.findById(junctionId).orElse(null);
        if (j == null) return List.of();
        return dataRepo.findTop20ByJunctionOrderByTimestampDesc(j).stream()
                .map(td -> new TrafficSnapshotDto(
                        td.getNorthLane(),
                        td.getEastLane(),
                        td.getSouthLane(),
                        td.getWestLane(),
                        td.getTotalQueue(),
                        td.getTimestamp()))
                .collect(Collectors.toList());
    }

    // ✅ NEW: Get all recent snapshots (for ApiController)
    public List<TrafficSnapshotDto> getTrafficSnapshots() {
        return dataRepo.findAll().stream()
                .map(td -> new TrafficSnapshotDto(
                        td.getNorthLane(),
                        td.getEastLane(),
                        td.getSouthLane(),
                        td.getWestLane(),
                        td.getTotalQueue(),
                        td.getTimestamp()))
                .collect(Collectors.toList());
    }

    // ✅ NEW: Get basic stats
    public String getTrafficStats() {
        long totalRecords = dataRepo.count();
        long congested = junctionRepo.findAll().stream().filter(Junction::isCongestionAlert).count();
        return "Total Records: " + totalRecords + ", Junctions with congestion: " + congested;
    }

    // ✅ NEW: Get alerts summary
    public List<String> getAlerts() {
        return junctionRepo.findAll().stream()
                .filter(Junction::isCongestionAlert)
                .map(j -> "⚠ Congestion at " + j.getName())
                .collect(Collectors.toList());
    }
}
