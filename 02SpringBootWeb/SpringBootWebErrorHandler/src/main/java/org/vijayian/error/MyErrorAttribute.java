package org.vijayian.error;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.Set;

/**
 * error attribute
 *
 * @author ViJay
 * @date 2021-01-17
 */
@Component
public class MyErrorAttribute extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        Set<String> keys = errorAttributes.keySet();
        System.out.println(keys);
        errorAttributes.put("custommsg", "Fail");
        return errorAttributes;
    }
}
