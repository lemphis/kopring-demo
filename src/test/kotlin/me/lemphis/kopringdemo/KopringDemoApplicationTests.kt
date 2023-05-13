package me.lemphis.kopringdemo

import me.lemphis.kopringdemo.config.RedisConfig
import me.lemphis.kopringdemo.config.RedisPublisher
import me.lemphis.kopringdemo.config.RedisSubscriber
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.context.annotation.Import

@DataRedisTest
@Import(RedisConfig::class, RedisPublisher::class, RedisSubscriber::class)
class KopringDemoApplicationTests {

	@Autowired
	private lateinit var redisPublisher: RedisPublisher

	@Autowired
	private lateinit var redisSubscriber: RedisSubscriber

	@Test
	fun redisPubSubTest() {
		val message = "mes"
		redisPublisher.publishMessage("demo-topic", message)

		val startTime = System.currentTimeMillis()
		val timeoutMillis = 500
		var receivedMessage = redisSubscriber.message
		while (receivedMessage == null && System.currentTimeMillis() - startTime < timeoutMillis) {
			receivedMessage = redisSubscriber.message
			Thread.sleep(50)
		}

		Assertions.assertEquals(redisSubscriber.message, message)
	}

}
