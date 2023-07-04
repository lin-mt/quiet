package cn.linmt.quiet.gateway.config;

import static org.springframework.security.config.Customizer.withDefaults;

import cn.linmt.quiet.gateway.properties.GatewayProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.client.RestTemplate;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@EnableReactiveMethodSecurity
@EnableConfigurationProperties(GatewayProperties.class)
public class GatewayConfig {

  @Bean
  public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
    // 日期序列化与反序列化
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    // 时间序列化与反序列化
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    builder
        .serializers(
            new LocalDateSerializer(dateFormatter), new LocalDateTimeSerializer(dateTimeFormatter))
        .deserializers(
            new LocalDateDeserializer(dateFormatter), new LocalDateDeserializer(dateTimeFormatter));
    return builder.createXmlMapper(false).build();
  }

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    http.authorizeExchange((exchange) -> exchange.anyExchange().authenticated());
    http.oauth2Login(withDefaults());
    http.oauth2Client(withDefaults());
    return http.build();
  }

  @Bean
  public RestTemplate restTemplate(ObjectMapper objectMapper) {
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setObjectMapper(objectMapper);
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(0, converter);
    return restTemplate;
  }
}
