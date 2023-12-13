package com.example.voiture.Service;

import com.example.voiture.Model.Voiture;
import com.example.voiture.Repository.VoitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoitureService {

    private final VoitureRepository voitureRepository;

    @Autowired
    public VoitureService(VoitureRepository voitureRepository) {

        this.voitureRepository = voitureRepository;
    }

    public Voiture enregistrerVoiture(Voiture voiture) {
        // Vous pouvez ajouter ici des logiques de validation ou de traitement avant d'enregistrer la voiture
        return voitureRepository.save(voiture);
    }

    // Ajoutez d'autres méthodes de service si nécessaire

}