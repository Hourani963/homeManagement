package com.ahmad.homeManagement.controlers;


import com.ahmad.homeManagement.modules.tabels.Article;
import com.ahmad.homeManagement.modules.tabels.Historique;
import com.ahmad.homeManagement.services.ArticleService;
import com.ahmad.homeManagement.services.HistoriqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("histo")
@CrossOrigin(origins = "*")
public class HistoriqueControler {

    private final HistoriqueService historiqueService;


    @Autowired
    public HistoriqueControler(HistoriqueService historiqueService) {
        this.historiqueService = historiqueService;

    }

    @GetMapping
    public List<Historique> getAllHistorique(){

        return historiqueService.findAll();
    }


}
