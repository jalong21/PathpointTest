package services

import scala.util.Using
import redis.clients.jedis.{Jedis, JedisPool}

object RedisService {

  // setup for redis in docker is in the ReadMe
  val jedisPool = new JedisPool("redis-stack", 6379)

  def getValue(key: String)  =
    Using(jedisPool.getResource) { jedis => jedis.get(key)}

  def setValue(key: String, value: String) =
    Using(jedisPool.getResource) { jedis => jedis.set(key, value)}
}
