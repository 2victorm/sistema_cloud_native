package com.duoc.sistemainscripcion.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitMQConfig {

    public static final String COLA_GUIAS = "cola-guias";
    public static final String COLA_ERRORES = "cola-errores";
    public static final String DLX_EXCHANGE = "dlx-exchange";

    // Cola principal donde se publican las guías
    @Bean
    public Queue colaGuias() {
        return QueueBuilder.durable(COLA_GUIAS)
                .withArgument("x-dead-letter-exchange", DLX_EXCHANGE)
                .build();
    }

    // Exchange del Dead Letter (para errores)
    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange(DLX_EXCHANGE);
    }

    // Cola 2: almacena los mensajes con error
    @Bean
    public Queue colaErrores() {
        return new Queue(COLA_ERRORES, true);
    }

    // Binding: conecta la cola de errores al DLX
    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(colaErrores())
                .to(dlxExchange())
                .with(COLA_GUIAS);
    }

    // Configuración para que mensajes fallidos vayan al DLX
    @Bean
    @Primary
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setDefaultRequeueRejected(false);
        return factory;
    }
}