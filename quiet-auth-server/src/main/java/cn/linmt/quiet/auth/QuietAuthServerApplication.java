package cn.linmt.quiet.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@SpringBootApplication
public class QuietAuthServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(QuietAuthServerApplication.class, args);
  }

  @Bean
  InMemoryUserDetailsManager inMemoryUserDetailsManager() {
    var one = User.withDefaultPasswordEncoder().roles("user").username("u").password("u").build();
    return new InMemoryUserDetailsManager(one);
  }
}
