package com.codewithraman.trafficease.repository;

import com.codewithraman.trafficease.model.TrafficData;
import com.codewithraman.trafficease.model.Junction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TrafficDataRepository extends JpaRepository<TrafficData, Long> {
    List<TrafficData> findTop20ByOrderByTimestampDesc();
    List<TrafficData> findTop20ByJunctionOrderByTimestampDesc(Junction junction);
}
