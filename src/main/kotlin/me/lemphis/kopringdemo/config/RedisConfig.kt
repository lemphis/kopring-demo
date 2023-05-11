package me.lemphis.kopringdemo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.serializer.GenericToStringSerializer

@Configuration
class RedisConfig {

	@Bean
	fun redisConnectionFactory(): RedisConnectionFactory = LettuceConnectionFactory()

	@Bean
	fun redisTemplate(
		redisConnectionFactory: RedisConnectionFactory,
	) = RedisTemplate<String, String>().apply {
		setConnectionFactory(redisConnectionFactory)
		valueSerializer = GenericToStringSerializer(String::class.java)
	}

	@Bean
	fun channelTopic() = ChannelTopic("demo-topic")

	@Bean
	fun redisMessageListenerContainer(
		redisConnectionFactory: RedisConnectionFactory,
		redisSubscriber: RedisSubscriber,
		channelTopic: ChannelTopic,
	) = RedisMessageListenerContainer().apply {
		setConnectionFactory(redisConnectionFactory)
		addMessageListener(redisSubscriber, channelTopic)
	}

}
