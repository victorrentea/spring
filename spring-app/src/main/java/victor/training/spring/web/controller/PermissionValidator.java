package victor.training.spring.web.controller;

import org.springframework.stereotype.Component;

@Component
public class PermissionValidator {
    public boolean checkCanUpdateTraining() {
        // DB call = ineficient.
        // 1) Browser-facing (intre tine si bro nu mai e nici o alta app cu creier, poate doar proxy/firewalluri)
            // ai sesiune cu brow < in ea tii userul cu drepturile lui = caching
            // in userul stocat in sesiunea cu Brow ideal tii toate permisiunile lui, incarcate la login, ai. sa nu ai nevoie sa le to queryiezi

            //!! dar daca perm userului se pot modifica DINAMIC in app, query la fiecare apel

        // 2) Microserviciul/REST API (mai e un api gateway intre tine/browser)
            // perm userului ar trebui sa vina pe TOKEN (OAuth) JWT token/ acess token
        return true;
    }
}
