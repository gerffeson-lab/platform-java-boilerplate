package com.acme.service.hello;

import com.acme.service.api.HelloApi;
import com.acme.service.api.model.HelloResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementa a interface gerada do contrato (api/openapi.yaml).
 * Mudou o contrato? Rode "mvn compile" e o compilador aponta o que ajustar.
 */
@RestController
public class HelloController implements HelloApi {

    @Override
    public ResponseEntity<HelloResponse> getHello(String name) {
        String who = (name == null || name.isBlank()) ? "mundo" : name;
        return ResponseEntity.ok(new HelloResponse().message("Olá, " + who + "!"));
    }
}
