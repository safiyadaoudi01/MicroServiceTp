package org.ENSAJ.ClientService.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nom;

	private Float age;

	public Client(long id, String nom, float age) {
	}

	// Vous n'avez pas besoin de déclarer de constructeurs manuels ici

	// Getters et setters générés par Lombok

}
