package com.example.aspects;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogicaCuDependenteExterne {
    private final ExternalSystemClient client;

    public String m() {
        return client.call().toUpperCase();
    }
}


@Service
class ExternalSystemClient {
    public String call() {
        throw new IllegalArgumentException("NU POTI CHEMA DIN TESTE. N_AR TREBUI");
    }

}