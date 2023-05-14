package com.wblazej;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.Test;

import static com.alibaba.fastjson2.JSON.parseArray;
import static com.alibaba.fastjson.JSON.toJSONString;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wblazej.models.atms.ATM;
import com.wblazej.services.ATMService;

public class ATMTest {

  byte[] example_request;
  String example_response;

  public ATMTest() {
    try {
      this.example_request = this.getClass().getResourceAsStream("atms/example_request.json").readAllBytes();
      byte[] response_bytes = this.getClass().getResourceAsStream("atms/example_response.json").readAllBytes();
      this.example_response = new String(response_bytes, StandardCharsets.UTF_8);
    } catch (IOException err) {
      System.out.println("Can't read file with testing dataset...");
    }
  }

  @Test
  public void test_sorting() {
    List<ATM> atms = parseArray(this.example_request, ATM.class);

    List<ATM> result = ATMService.sort(atms);

    String serialized = toJSONString(result, SerializerFeature.PrettyFormat);
    assertEquals(this.example_response, serialized);
  }
}
