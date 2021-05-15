package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Capture;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Capture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaptureRepository extends JpaRepository<Capture, Long> {}
