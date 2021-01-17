package org.vijayian.fastjson;//package org.vijayian.fastjson;
//
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.alibaba.fastjson.support.config.FastJsonConfig;
//import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import java.nio.charset.StandardCharsets;
//
///**
// * fastjson config
// *
// * @author ViJay
// * @date 2021-01-17
// */
//@Configuration
//public class FastJsonConfiguartion {
//
//    @Bean
//    FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
//        //>> TODO 创建FastJsonHttpMessageConverter.
//        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
//        //>> TODO 创建配置FastJsonConfig.
//        FastJsonConfig config = new FastJsonConfig();
//        config.setDateFormat("yyyy-MM-dd");
//        config.setCharset(StandardCharsets.UTF_8);
//        config.setSerializerFeatures(
//                SerializerFeature.WriteMapNullValue,
//                SerializerFeature.PrettyFormat,
//                SerializerFeature.WriteNullListAsEmpty,
//                SerializerFeature.WriteNullStringAsEmpty);
//        //>> TODO 设置FastJsonConfig.
//        converter.setFastJsonConfig(config);
//        return converter;
//    }
//}
