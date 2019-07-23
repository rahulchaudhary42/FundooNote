package com.bridgelabz.fundoo.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
	
	@Value("${fundoo.rabbitmq.queue}")
	String queueName;
	
	@Value("${fundoo.rabbitmq.exchange}")
	String exchange;
	
	@Value("${fundoo.rabbitmq.routingkey}")
	private String routingkey;
	
	/**
	 * Purpose:Provides Bean for Queue(Mailing Queue)
	 * @return Bean for Queue (Mailing Queue)
	 */
	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}
	
	/**
	 * Purpose: Provides Bean for exchange(Mailing Exchange)
	 * @return Exchange Bean
	 */
	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange);
	}
	
	/**
	 * @param queue:-Queue name
	 * @param exchange:-Direct Exchange
	 * @return
	 */
	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingkey);
	}
	
	/**
	 * Purpose:- For Converting Message(Jackson to json Message)
	 * @return 
	 */
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
//	@Bean
//	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//		rabbitTemplate.setMessageConverter(jsonMessageConverter());
//		return rabbitTemplate;
//	}
	

}
