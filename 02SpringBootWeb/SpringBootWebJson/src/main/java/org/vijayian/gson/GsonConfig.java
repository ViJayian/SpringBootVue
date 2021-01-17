package org.vijayian.gson;//package org.vijayian.gson;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.json.GsonHttpMessageConverter;
//
//import java.lang.reflect.Modifier;
//
///**
// * config
// *
// * @author ViJay
// * @date 2021-01-16
// */
//@Configuration
//public class GsonConfig {
//
//    @Bean
//    public GsonHttpMessageConverter converter(){
//        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
//        GsonBuilder builder = new GsonBuilder();
//        //>> TODO gson日期格式化.
//        builder.setDateFormat("yyyy-MM-dd");
//        //>> TODO protected类型的不被序列化.
//        builder.excludeFieldsWithModifiers(Modifier.PROTECTED);
//        Gson gson = builder.create();
//        converter.setGson(gson);
//        return converter;
//    }
//
//}
