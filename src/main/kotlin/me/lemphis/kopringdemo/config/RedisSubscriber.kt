package me.lemphis.kopringdemo.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener

@Configuration
class RedisSubscriber : MessageListener {

	override fun onMessage(message: Message, pattern: ByteArray?) {
		val channel = String(message.channel)
		val content = String(message.body)
		println("Received message from channel: $channel, content: $content")
	}

}
