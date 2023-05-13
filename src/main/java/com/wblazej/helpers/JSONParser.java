package com.wblazej.helpers;

import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;

import io.activej.bytebuf.ByteBuf;
import io.activej.http.HttpRequest;

public class JSONParser {
  public static <T> List<T> parseArray(HttpRequest request, Class<T> clazz) throws JSONException, InterruptedException {
    ByteBuf body = request.getBody();
    byte[] bodyBytes = body.getArray();

    return JSON.parseArray(bodyBytes, clazz);
  }
}
