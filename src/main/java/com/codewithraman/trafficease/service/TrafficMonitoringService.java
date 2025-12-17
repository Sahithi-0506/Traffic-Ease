package com.codewithraman.trafficease.service;

import com.codewithraman.trafficease.model.Junction;
import com.codewithraman.trafficease.model.TrafficData;
import com.codewithraman.trafficease.repository.JunctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TrafficMonitoringService {

    private static final int CONGESTION_THRESHOLD = 100;

    @Value("${alert.email.to}")
    private String alertEmailTo;

    @Autowired
    private AlertService alertService;

    @Autowired
    private JunctionRepository junctionRepository;

    public void checkForCongestion(Junction j, TrafficData d) {
        if (d.getTotalQueue() > CONGESTION_THRESHOLD) {
            LocalDateTime now = LocalDateTime.now();

            // Only send alert if last alert was more than 60 seconds ago or never sent before
            if (j.getLastAlertTime() == null || j.getLastAlertTime().isBefore(now.minusSeconds(60))) {
                j.setCongestionAlert(true);

                // Send SMS and Email alerts
                alertService.sendSms("High congestion at " + j.getName() + " (total: " + d.getTotalQueue() + ")");
                alertService.sendEmail(
                        "Traffic Alert - " + j.getName(),
                        "High congestion at " + j.getName() + " (total: " + d.getTotalQueue() + ")",
                        alertEmailTo
                );

                // Update last alert time and save to database
                j.setLastAlertTime(now);
                junctionRepository.save(j);
                System.out.println("Alert sent. Updated lastAlertTime to: " + now);
            }
        } else {
            // Clear alert flag if congestion subsides
            if (j.isCongestionAlert()) {
                j.setCongestionAlert(false);
                junctionRepository.save(j);
                System.out.println("Congestion cleared for " + j.getName());
            }
        }
    }
}
