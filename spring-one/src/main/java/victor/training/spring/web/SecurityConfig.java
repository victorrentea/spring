package victor.training.spring.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    // TODO [SEC] Start with ROLE-based authorization on URL-patterns

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
            .csrf().disable()
            // in viata reala, vrei CSRF activat : asta inseamna ca la orice request AJAX trimis trebuie sa adaugi un header CSRF ca protectie aditionala
            .authorizeRequests()
                .mvcMatchers("unsecured/**").permitAll()
//                .mvcMatchers(HttpMethod.DELETE, "rest/trainings/*").hasRole("USER")
                .anyRequest().authenticated()
            .and()
            .formLogin()
            ;
    }


    // hashing:
    // 1) one-way . adica de la h("a") --->nu poti determina "a"
    // 2) h("a") == h("a")
    // 3) dispersie mare ('imprastie bine valoride de output')
           // --> probabilitate ca hash(2g) == hash(2g')  <  probab ca in urm 0.001s un asteroid sa distruga complet pamantul

    // h("a") = 1  ===> ESTI P**ST pt ca orice input ii dai va zice ca parola e buna
    // Algo de hash bcrypt, sha256-512, MD5

    // DB -> "{algo}df&^$&@%^"
       // Risc: un hacker daca cumva are acces la baza poate sa suprascrie un parola hashuita cu un hash al unei parole pe care o stie-> va avea acces in continuare fara sa se prinda nimeni (userul pe care l-a atacat a vazut ca nu fusese folosit de 1 an)

    // nu stochezi parola userului ci trimiti TU parola pe care el ti-o introduce in login catre un sistem tert ca sa verifice ala
       // Risc: Un destept sa logeze in .log toate requesturile outbound (cu tot parole).

    // "o trimit hashuita" --> nu in clar parola ci tot gen {algo}df&^$&@%^  <-- pasta o trimiti
      // a) tert face hashul primit == cu ce are el in baza
      // b) daca tert are parolele in clar, hace hash(parola din DB lui) == ce trimit tu

    // Risc: brute force: sa refoloseti combinatia user/parola pe mai multe siteur

    // Risc: Replay Attack  + plezneste URL-uri cu curl in care sa refoloseasca hash-ul parolei interceptat
       // Fix: "expiri tokenul"  sau "salt seed" --> "Digest Authentication"
        // Fix: in token pui un random number, iar serverul tine minte toate randomurile pe care le-a tratat (respinge duplicate)
                // ===> Risk: Intercepteaza request. Il blocheaza. Si iti foloseste EL atacatorul tokeunul






    // *** Dummy users 100% in-mem
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder().username("user").password("user").roles("USER").build();
        UserDetails adminDetails = User.withDefaultPasswordEncoder().username("admin").password("admin").roles("ADMIN").build();
        return new InMemoryUserDetailsManager(userDetails, adminDetails);
    }

    // ... then, switch to loading user data from DB:
    // *** Also loading data from DB
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new DatabaseUserDetailsService();
//    }

}
