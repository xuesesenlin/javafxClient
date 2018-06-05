package org.fx.urils;

import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

/**
 * @author ld
 * @name
 * @table
 * @remarks
 */
public class FeignUtil {

    public static Feign.Builder feign() {
        return Feign.builder()
//                    编码方式
                .encoder(new JacksonEncoder())
//                    解码方式
                .decoder(new JacksonDecoder())
//                    超时时长
                .options(new Request.Options(1000, 3500))
//                    重试策略
                .retryer(new Retryer.Default(5000, 5000, 3));
    }
}
