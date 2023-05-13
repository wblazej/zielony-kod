package com.wblazej.controllers;

import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.wblazej.helpers.JSONParser;
import com.wblazej.models.atms.ATM;
import com.wblazej.services.ATMService;

import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promise;

public class ATMController {
  public static Promise<HttpResponse> handle(HttpRequest request) {
    return request.loadBody().map($ -> {
      try {
        List<ATM> data = JSONParser.parseArray(request, ATM.class);

        List<ATM> sorted_atms = ATMService.sort(data);

        return HttpResponse.ok200()
            .withJson(JSON.toJSONString(sorted_atms));
      } catch (JSONException e) {
        return HttpResponse.ofCode(400);
      }
    });
  }
}
