package com.mg.axechen.wanandroid.network.converter;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONObject;

import java.lang.reflect.Type;

import network.response.Response;

/**
 * 当出现data为null的时候会出现空指针，在这个地方处理
 */
public class ResponseTypeAdapter implements JsonDeserializer<Response> {
    @Override
    public Response deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        if (json.isJsonObject()) {
            final JsonObject obj = json.getAsJsonObject();
            if (obj.get("data").isJsonNull()) {
                return new Response(new JSONObject(), obj.get("errorCode").getAsInt(), obj.get("errorMsg").getAsString());
            } else {
                return new Gson().fromJson(json, typeOfT);
            }
        }
        return null;
    }


}
