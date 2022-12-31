package com.ahmad.homeManagement.modules;

import com.ahmad.homeManagement.modules.tabels.Historique;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoriqueRepository extends JpaRepository<Historique, Long> {
}