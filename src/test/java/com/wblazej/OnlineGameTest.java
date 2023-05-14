package com.wblazej;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson2.JSON;
import com.wblazej.models.onlinegame.Clan;
import com.wblazej.models.onlinegame.Request;
import com.wblazej.services.OnlineGameService;

public class OnlineGameTest {

  byte[] example_request;
  String example_response;

  public OnlineGameTest() {
    try {
      this.example_request = this.getClass().getResourceAsStream("onlinegame/example_request.json").readAllBytes();
      byte[] response_bytes = this.getClass().getResourceAsStream("onlinegame/example_response.json").readAllBytes();
      this.example_response = new String(response_bytes, StandardCharsets.UTF_8);
    } catch (IOException err) {
      System.out.println("Can't read file with testing dataset...");
    }
  }

  @Test
  public void test_processing() {
    Request data = JSON.parseObject(this.example_request, Request.class);

    List<List<Clan>> queue = OnlineGameService.process_queue(data);

    String serialized = JSON.toJSONString(queue);
    assertEquals(this.example_response, serialized);
  }
}
