package solutions.simurgh.core.common.logging.access;

import jakarta.servlet.DispatcherType;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.EnumSet;

@Configuration
public class HttpAccessLogConfiguration {

  @Bean
  public FilterRegistrationBean<HttpAccessLogFilter>
  httpAccessLogFilter() {
    FilterRegistrationBean<HttpAccessLogFilter> registration =
            new FilterRegistrationBean<>();
    registration.setFilter(new HttpAccessLogFilter());
    registration.addUrlPatterns("/*");
    registration.setDispatcherTypes(
            EnumSet.of(DispatcherType.REQUEST)
    );
    registration.setName("httpAccessLogFilter");
    registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return registration;
  }
}
