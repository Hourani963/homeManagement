package com.ahmad.homeManagement.services;


import com.ahmad.homeManagement.modules.HistoriqueRepository;
import com.ahmad.homeManagement.modules.tabels.Historique;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoriqueService {

    private final HistoriqueRepository historiqueRepository;

    public HistoriqueService(HistoriqueRepository historiqueRepository) {
        this.historiqueRepository = historiqueRepository;
    }


    public List<Historique> findAll() {
        return historiqueRepository.findAll();
    }
}
