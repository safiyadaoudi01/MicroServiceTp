package com.example.voiture.Service;

import com.example.voiture.Model.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="SERVICE-CLIENT")
@Component
@Service
public interface ClientService{
    @GetMapping(path="/client/{id}")
    public Client clientById(@PathVariable Long id);
}
