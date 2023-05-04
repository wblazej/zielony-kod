package com.wblazej;

import io.activej.http.AsyncServlet;
import io.activej.http.HttpResponse;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.launcher.Launcher;
import io.activej.launchers.http.HttpServerLauncher;

import static io.activej.http.HttpMethod.POST;

public class Application extends HttpServerLauncher {

  @Provides
  AsyncServlet servlet() {
    return RoutingServlet.create().map(POST, "/atm", request -> {
      return HttpResponse.ok200().withHtml("hello, there!");
    });
  }

  public static void main(String[] args) throws Exception {
    Launcher launcher = new Application();
    launcher.launch(args);
  }
}