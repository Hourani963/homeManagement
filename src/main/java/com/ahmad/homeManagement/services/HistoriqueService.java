package com.ahmad.homeManagement.services;


import com.ahmad.homeManagement.modules.HistoriqueRepository;
import com.ahmad.homeManagement.modules.tabels.Article;
import com.ahmad.homeManagement.modules.tabels.Historique;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HistoriqueService {

    private final HistoriqueRepository historiqueRepository;
    private final ArticleService articleService;

    public HistoriqueService(HistoriqueRepository historiqueRepository, ArticleService articleService) {
        this.historiqueRepository = historiqueRepository;
        this.articleService = articleService;
    }


    public List<Historique> findAll() {

        return historiqueRepository.findAll().stream().map(
                historique -> {
                    historique.setNomArticle(articleService.getPerformerById(historique.getIdArticle()).getNom());
                    return historique;
        }).collect(Collectors.toList());
    }
}


