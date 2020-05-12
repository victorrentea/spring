package com.example.demo.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// Nu sunati, mai coboram noi din cand in cand.
@Component
@Slf4j
public class FolderPoller {

    @Scheduled(fixedRateString = "${folder.polling.rate.millis}")
    public void polling() {
        log.debug("MA UIT IN FOLDER");
    }

    @Scheduled(cron = "${refresh.referentials.cron}")
    public void refeshReferentials() {
        log.debug("FAC UN GET SI UN IMPORT DE DATE DE REF");
    }

    // sa-ti pornesti singur joburile, chiar la rate configurabile din props
    // e rigid. necesita restart de multe ori.

    // alternativa: in loc sa-ti pornesti de capul tau procesarea,
    // expui un HTTP endpoint, accesibil (prin Spring Security) doar
    // catre localhost: adica poti sa-l invoci cu "curl" doar de pe aceeasi masina.

    // comanda "at" permite schedularea rularii unor CURL-uri din unix.
}
