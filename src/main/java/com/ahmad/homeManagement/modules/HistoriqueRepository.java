package com.ahmad.homeManagement.modules;

import com.ahmad.homeManagement.modules.tabels.Category;
import com.ahmad.homeManagement.modules.tabels.Historique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoriqueRepository extends JpaRepository<Historique, Long> {


    @Query("SELECT h FROM Historique h ORDER BY h.idHisto desc ")
    List<Historique> findAllHisto();
}