package com.wblazej.controllers;

import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.wblazej.helpers.JSONParser;
import com.wblazej.models.transactions.Account;
import com.wblazej.models.transactions.Transaction;
import com.wblazej.services.TransactionService;

import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promise;

public class TransactionController {
  public static Promise<HttpResponse> handle(HttpRequest request) {
    return request.loadBody().map($ -> {
      try {
        List<Transaction> transactions = JSONParser.parseArray(request, Transaction.class);

        List<Account> processed_transactions = TransactionService.process(transactions);

        return HttpResponse.ok200()
            .withJson(JSON.toJSONString(processed_transactions));
      } catch (JSONException e) {
        return HttpResponse.ofCode(400);
      }
    });
  }
}
