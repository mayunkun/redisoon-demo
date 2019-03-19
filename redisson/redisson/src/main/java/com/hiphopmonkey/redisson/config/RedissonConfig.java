package com.hiphopmonkey.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class RedissonConfig {
	
	@Value("${spring.profiles.active}")
	private String profiles;

    @Bean
    public RedissonClient redisson() throws IOException {
		/** 仅生产环境使用REDIS集群 */
    	if("prod".equals(profiles)) {
            Config config = Config.fromYAML(RedissonConfig.class.getClassLoader().getResource("redisson-config-colony.yml"));
            return Redisson.create(config);
    	} else {
            Config config = Config.fromYAML(RedissonConfig.class.getClassLoader().getResource("redisson-config.yml"));
            return Redisson.create(config);
    	}
    }
}