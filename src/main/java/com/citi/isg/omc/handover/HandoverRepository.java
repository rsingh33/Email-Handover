package com.citi.isg.omc.handover;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HandoverRepository extends JpaRepository<HandoverRow, Long> {
    List<HandoverRow> findByReportedByStartsWithIgnoreCase(String reportedBy);


}
