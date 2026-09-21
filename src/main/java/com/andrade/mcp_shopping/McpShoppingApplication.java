package com.andrade.mcp_shopping;

import java.util.List;

import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.andrade.mcp_shopping.service.ShoppingCart;

@SpringBootApplication
public class McpShoppingApplication {

	public static void main(String[] args) {
		SpringApplication.run(McpShoppingApplication.class, args);
	}

	@Bean
	public List<ToolCallback> tools(ShoppingCart shoppingCard) {
		return List.of(ToolCallbacks.from(shoppingCard));
	}

}
