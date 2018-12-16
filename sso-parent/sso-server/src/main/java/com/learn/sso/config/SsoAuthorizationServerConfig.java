package com.learn.sso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class SsoAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	/* 添加给哪些应用可以访问该认证 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("imooc1").secret("imoocsecrect1")
				.authorizedGrantTypes("authorization_code", "refrsh_token").scopes("all").and().withClient("imooc2")
				.secret("imoocsecrect2").authorizedGrantTypes("authorization_code", "refrsh_token").scopes("all");

	}
	
	
	
	
	/**
	 * 认证服务器的安全配置
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("isAuthenticated()");
	}

	/**
	 * 生成令牌的配置
	 */
    @Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints)throws Exception {
		endpoints.tokenStore(jwtTokenStore()).accessTokenConverter(jwtAccessTokenConverter());
	}
	
	
	@Bean
	public TokenStore jwtTokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("imooc");
		return converter;
	}

}
