package com.ishimweemmy.templates.springboot.v1.repositories;

import com.ishimweemmy.templates.springboot.v1.models.Banking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface IBankingRepository extends JpaRepository<Banking, UUID> {
  Page<Banking> findByAccountId(UUID accountId, Pageable pageable);
}
