package com.example.demo.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfig {
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		// プロパティ名が厳密に一致するもののみマッピングする
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		// 型が一致するもののみマッピングする
		modelMapper.getConfiguration().setFullTypeMatchingRequired(true);
		
		return modelMapper;
	}
}