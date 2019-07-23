package com.bridgelabz.fundoo.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bridgelabz.fundoo.util.MessageListener;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;

//import com.rabbitmq.client.ConnectionFactory;

@Configuration
public class RabbitMqConfig {

	@Value("${fundoo.rabbitmq.queue}")
	String queueName;

	@Value("${fundoo.rabbitmq.exchange}")
	String exchange;

	@Value("${fundoo.rabbitmq.routingkey}")
	private String routingkey;

	@Value("${elastic.rabbitmq.queue}")
	String queueName1;

	@Value("${elastic.rabbitmq.routingkey}")
	private String routingkey1;

	@Value("${elastic.rabbitmq.exchange}")
	String exchange1;

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange);
	}

	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingkey);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	Queue queue1() {
		return new Queue(queueName1, false);
	}

	@Bean
	DirectExchange exchange1() {
		return new DirectExchange(exchange1);
	}

	@Bean
	Binding binding1(Queue queue1, DirectExchange exchange1) {
		return BindingBuilder.bind(queue1).to(exchange1).with(routingkey1);
	}

//	@Bean
//	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//		rabbitTemplate.setMessageConverter(jsonMessageConverter());
//		return rabbitTemplate;
//	}
	@Bean
	 SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
	 MessageListenerAdapter listenerAdapter) {
	  SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
	  container.setConnectionFactory(connectionFactory);
	  container.setQueueNames(queueName1);
	  container.setMessageListener(listenerAdapter);
	  return container;
	 }
	 @Bean
	 MessageListenerAdapter myQueueListener(MessageListener listener) {
	  return new MessageListenerAdapter(listener, "onMessage");
	 }
}
