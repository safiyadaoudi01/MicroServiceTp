package com.example.voiture.Controller;

import com.example.voiture.Model.Client;
import com.example.voiture.Model.Voiture;
import com.example.voiture.Repository.VoitureRepository;
import com.example.voiture.Service.ClientService;
import com.example.voiture.Service.VoitureService;
import com.example.voiture.VoitureApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VoitureController {


        @Autowired
        VoitureRepository voitureRepository;

        @Autowired
        VoitureService voitureService;

    @Autowired
    private final ClientService clientService;

    @Autowired
    public VoitureController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "/voitures", produces = {"application/json"})
    public ResponseEntity<Object> findAll() {
        try {
            List<Voiture> voitures = voitureRepository.findAll();

            // Pour chaque voiture, récupérez les détails du client et mettez à jour l'objet Voiture
            for (Voiture voiture : voitures) {
                Client client = clientService.clientById(voiture.getClientId());
                voiture.setClient(client);
            }

            return ResponseEntity.ok(voitures);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching voitures: " + e.getMessage());
        }
    }


    @GetMapping("/voitures/{Id}")
        public ResponseEntity<Object> findById(@PathVariable Long Id) {
            try {
                Voiture voiture = voitureRepository.findById(Id)
                        .orElseThrow(() -> new Exception("Voiture Introuvable"));

                // Fetch the client details using the clientService
                voiture.setClient(clientService.clientById(voiture.getClientId()));

                return ResponseEntity.ok(voiture);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Voiture not found with ID: " + Id);
            }
        }

        @GetMapping("/voitures/client/{Id}")
        public ResponseEntity<List<Voiture>> findByClient(@PathVariable Long Id) {
            try {
                Client client = clientService.clientById(Id);
                if (client != null) {
                    List<Voiture> voitures = voitureRepository.findByClientId(Id);
                    return ResponseEntity.ok(voitures);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        @PostMapping("/voitures/{clientId}")
        public ResponseEntity<Object> save(@PathVariable Long clientId, @RequestBody Voiture voiture) {
            try {
                // Fetch the client details using the clientService
                Client client = clientService.clientById(clientId);

                if (client != null) {
                    // Set the fetched client in the voiture object
                    voiture.setClient(client);
                    // Save the Voiture with the associated Client
                    voiture.setClientId(clientId);
                    Voiture savedVoiture = voitureService.enregistrerVoiture(voiture);

                    return ResponseEntity.ok(savedVoiture);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Client not found with ID: " + clientId);
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error saving voiture: " + e.getMessage());
            }
        }

        @PutMapping("/voitures/{Id}")
        public ResponseEntity<Object> update(@PathVariable Long Id, @RequestBody Voiture updatedVoiture) {
            try {
                Voiture existingVoiture = voitureRepository.findById(Id)
                        .orElseThrow(() -> new Exception("Voiture not found with ID: " + Id));

                // Update only the non-null fields from the request body
                if (updatedVoiture.getMatricule() != null && !updatedVoiture.getMatricule().isEmpty()) {
                    existingVoiture.setMatricule(updatedVoiture.getMatricule());
                }

                if (updatedVoiture.getMarque() != null && !updatedVoiture.getMarque().isEmpty()) {
                    existingVoiture.setMarque(updatedVoiture.getMarque());
                }

                if (updatedVoiture.getModel() != null && !updatedVoiture.getModel().isEmpty()) {
                    existingVoiture.setModel(updatedVoiture.getModel());
                }

                // Save the updated Voiture
                Voiture savedVoiture = voitureRepository.save(existingVoiture);

                return ResponseEntity.ok(savedVoiture);

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error updating voiture: " + e.getMessage());
            }
        }
    @Bean
    CommandLineRunner initialiserBaseH2(VoitureRepository voitureRepository, ClientService clientService){

        return args -> {
            Client c1 = clientService.clientById(2L);
            Client c2 = clientService.clientById(1L);
            System.out.println("**************************");
            System.out.println("Id est :" + c2.getId());
            System.out.println("Nom est :" + c2.getNom());
            System.out.println("**************************");
            System.out.println("**************************");
            System.out.println("Id est :" + c1.getId());
            System.out.println("Nom est :" + c1.getNom());
            System.out.println("Nom est :" + c1.getAge());
            System.out.println("**************************");
            voitureRepository.save(new Voiture(Long.parseLong("1"), "Toyota", "A 25 333", "Corolla", 1L, c2));
            voitureRepository.save(new Voiture(Long.parseLong("2"), "Renault", "B 6 3456", "Megane", 1L, c2));
            voitureRepository.save(new Voiture(Long.parseLong("3"), "Peugeot", "A 55 4444", "301", 2L, c1));
        };
    }

}
