package com.tianqi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 * @author yuantianqi
 */
@Configuration
public class CorsConfig {
  // 设置允许跨域的源
  private static String[] originsVal = new String[]{
      "127.0.0.1:8080",
      "localhost:8080",
      "www.movie.com:8082"
  };

  /**
   * 跨域过滤器
   *
   * @return
   */
  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    this.addAllowedOrigins(corsConfiguration);
    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.addAllowedMethod("*");
    corsConfiguration.setAllowCredentials(true);
    source.registerCorsConfiguration("/**", corsConfiguration);
    return new CorsFilter(source);
  }

  private void addAllowedOrigins(CorsConfiguration corsConfiguration) {
    for (String origin : originsVal) {
      corsConfiguration.addAllowedOrigin("http://" + origin);
      corsConfiguration.addAllowedOrigin("https://" + origin);
    }
  }
}