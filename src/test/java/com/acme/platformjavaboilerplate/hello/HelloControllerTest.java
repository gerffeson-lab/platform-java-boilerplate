package com.acme.platformjavaboilerplate.hello;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HelloController.class)
class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void retornaSaudacaoComNome() throws Exception {
        mvc.perform(get("/v1/hello").param("name", "Ana"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Olá, Ana!"));
    }

    @Test
    void retornaSaudacaoPadraoSemNome() throws Exception {
        mvc.perform(get("/v1/hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Olá, mundo!"));
    }
}
