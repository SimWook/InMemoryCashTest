package com.example.inmemorycashtest;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKey;

import java.lang.reflect.Method;

/**
 * 特定のロジックを通じてキーを生成したい場合に使用されます。
 * KeyGeneratorを継承して実装すればよいです。
 */
public class SimpleKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        return makeKey(params);
    }

    public static Object makeKey(Object... params) {
        if (params.length == 0) {
            return SimpleKey.EMPTY;
        } else {
            return params[0];
        }
    }
}
