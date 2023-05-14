package com.wblazej;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;

import static com.alibaba.fastjson2.JSON.parseArray;
import static org.junit.Assert.assertEquals;

import com.wblazej.models.transactions.Account;
import com.wblazej.models.transactions.Transaction;
import com.wblazej.services.TransactionService;

public class TransactionTest {

  byte[] example_request;
  byte[] example_response;

  public TransactionTest() {
    try {
      this.example_request = this.getClass().getResourceAsStream("transactions/example_request.json").readAllBytes();
      this.example_response = this.getClass().getResourceAsStream("transactions/example_response.json").readAllBytes();
    } catch (IOException err) {
      System.out.println("Can't read file with testing dataset...");
    }
  }

  @Test
  public void test_processing() {
    List<Transaction> transactions = parseArray(this.example_request, Transaction.class);
    List<Account> expected_result = parseArray(this.example_response, Account.class);

    List<Account> result = TransactionService.process(transactions);

    assertEquals(expected_result.size(), result.size());
    IntStream.range(0, expected_result.size()).forEach(i -> {
      Account expected = expected_result.get(i);
      Account actual = result.get(i);

      assertEquals(expected.account, actual.account);
      assertEquals(expected.balance, actual.balance, 0.05);
      assertEquals(expected.creditCount, actual.creditCount);
      assertEquals(expected.debitCount, actual.debitCount);
    });
  }
}
