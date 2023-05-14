package com.wblazej;

import io.activej.http.AsyncServlet;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.launcher.Launcher;
import io.activej.launchers.http.HttpServerLauncher;

import static io.activej.http.HttpMethod.POST;

import com.wblazej.controllers.TransactionController;
import com.wblazej.controllers.OnlineGameController;
import com.wblazej.controllers.ATMController;

public class Application extends HttpServerLauncher {

  @Provides
  AsyncServlet servlet() {
    return RoutingServlet.create()
        .map(POST, "/atms/calculateOrder", ATMController::handle)
        .map(POST, "/transactions/report", TransactionController::handle)
        .map(POST, "/onlinegame/calculate", OnlineGameController::handle);
  }

  public static void main(String[] args) throws Exception {
    Launcher launcher = new Application();
    launcher.launch(args);
  }
}