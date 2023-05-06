package com.wblazej;

import io.activej.http.AsyncServlet;
import io.activej.http.HttpResponse;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.launcher.Launcher;
import io.activej.launchers.http.HttpServerLauncher;

import static io.activej.http.HttpMethod.POST;

import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.wblazej.models.atms.ATM;
import com.wblazej.models.transactions.Transaction;
import com.wblazej.services.ATMService;
import com.wblazej.services.TransactionService;

import io.activej.bytebuf.ByteBuf;

public class Application extends HttpServerLauncher {

  @Provides
  AsyncServlet servlet() {
    return RoutingServlet.create()
        .map(POST, "/atm", request -> request.loadBody().map($ -> {
          ByteBuf body = request.getBody();
          byte[] bodyBytes = body.getArray();

          try {
            List<ATM> data = JSON.parseArray(bodyBytes, ATM.class);

            return HttpResponse.ok200().withJson(JSON.toJSONString(ATMService.sort(data)));
          } catch (JSONException e) {
            return HttpResponse.ofCode(400);
          }
        })).map(POST, "/transaction", request -> request.loadBody().map($ -> {
          ByteBuf body = request.getBody();
          byte[] bodyBytes = body.getArray();

          try {
            List<Transaction> transactions = JSON.parseArray(bodyBytes, Transaction.class);
            return HttpResponse.ok200()
                .withJson(JSON.toJSONString(TransactionService.process(transactions)));
          } catch (JSONException e) {
            return HttpResponse.ofCode(400);
          }
        }));
  }

  public static void main(String[] args) throws Exception {
    Launcher launcher = new Application();
    launcher.launch(args);
  }
}