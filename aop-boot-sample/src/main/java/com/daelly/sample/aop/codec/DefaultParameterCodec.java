package com.daelly.sample.aop.codec;

import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;

@Slf4j
public class DefaultParameterCodec implements ParameterCodec {

    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    @Override
    public String encode(MethodParameter[] parameters, Object[] args) {
        JsonObject jsonObject = new JsonObject();
        for (int i = 0; i < parameters.length; i++) {
            MethodParameter param = parameters[i];
            Object arg = args[i];
            if (arg == null) {
                continue;
            }

            JsonElement jsonElement = gson.toJsonTree(arg, param.getParameterType());
            jsonObject.add(param.getParameterName(), jsonElement);
        }

        return jsonObject.toString();
    }

    @Override
    public Object[] decode(MethodParameter[] parameters, String content) {
        try {
            Object[] result = new Object[parameters.length];
            JsonElement jsonElement = new JsonParser().parse(content);
            Assert.isTrue(jsonElement.isJsonObject(), "content must be json object");
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            for (int i = 0; i < parameters.length; i++) {
                MethodParameter param = parameters[i];
                String paramName = param.getParameterName();
                JsonElement element = jsonObject.get(paramName);
                if (element == null || element.isJsonNull()) {
                    result[i] = null;
                    continue;
                }

                Object arg = gson.fromJson(element, param.getParameterType());
                result[i] = arg;
            }
            return result;
        } catch (Throwable t) {
            log.error("DefaultParameterCodec parse parameters fail with content:{}", content, t);
        }

        return null;
    }
}
