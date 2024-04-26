package com.example.inmemorycashtest.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
@EnableRedisRepositories
public class RedisConfig {
    private final RedisProperties redisProperties;

    /**
     * RedisConnectionFactoryについて：
     * - JavaでサポートされているRedisClientは2種類あります。（Jedis、Lettuce）
     * - Jedisはマルチスレッド環境で不安定であり、プールの制限などの理由から、現在Spring Boot 2.0以上ではLettuceが搭載され、使用されています。
     * - RedisPropertiesはRedisの基本設定を持っています。
     * - RedisPropertiesを利用してRedisのホストとポートを設定します。
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        return new LettuceConnectionFactory(redisProperties.getHost(),redisProperties.getPort());
    }

    /**
     * RedisTemplateについての説明：
     * Redisにデータを保存する方法は合計2つあります。
     * 1. CrudRepositoryを継承して使用
     *    - Redisを使用するリポジトリを作成し、CrudRepositoryを継承して使用します。
     * 2. RedisTemplateを定義して使用
     *    - Redisに対して直接、シリアライズ設定を行って保存します。自動的にシリアライズを実行します。
     *    - ConnectionFactoryにはLettuceを設定します。
     *    - setKeySerializer(new StringRedisSerializer()): Stringとして入力されるキーに対するシリアライズを行います。つまり、キーに対するシリアライズ設定を行うメソッドです。
     *    - setValueSerializer(new GenericJackson2JsonRedisSerializer()): 保存したいObjectオブジェクトをシリアライズして保存します。つまり、値に対するシリアライズ設定を行うメソッドです。
     *    - RedisTemplate<String,?>: キー値は常にStringなので、String型を使い、値はワイルドカードを使用して、さまざまなオブジェクトを保存できるようにします。
     */
    @Bean
    public RedisTemplate<?,?> redisTemplate(){
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
}
