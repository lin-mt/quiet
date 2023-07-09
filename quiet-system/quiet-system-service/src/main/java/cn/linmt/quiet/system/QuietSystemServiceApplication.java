package cn.linmt.quiet.system;

import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableWebSecurity
@EnableMethodSecurity
@SpringBootApplication
public class QuietSystemServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(QuietSystemServiceApplication.class, args);
  }

  @GetMapping("/")
  @PreAuthorize("hasAuthority('SCOPE_user.read')")
  public Map<String, String> read() {
    return Map.of("message", "read");
  }
}
