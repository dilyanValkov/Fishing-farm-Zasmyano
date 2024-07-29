package com.valkov.fishingfarm.repository.bungalow;

import com.valkov.fishingfarm.domain.model.Bungalow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BungalowRepository extends JpaRepository<Bungalow, Long> {
}
