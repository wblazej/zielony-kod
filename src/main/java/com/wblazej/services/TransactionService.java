package com.wblazej.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wblazej.models.transactions.Account;
import com.wblazej.models.transactions.Transaction;

public class TransactionService {
  public static List<Account> process(List<Transaction> transactions) {
    Map<String, Account> accounts = new HashMap<>();

    transactions.forEach(transaction -> {
      if (accounts.containsKey(transaction.debitAccount)) {
        Account acc = accounts.get(transaction.debitAccount);
        acc.debitCount += 1;
        acc.balance -= transaction.amount;
      } else {
        Account acc = new Account(
            transaction.debitAccount,
            1,
            0,
            -transaction.amount);
        accounts.put(transaction.debitAccount, acc);
      }

      if (accounts.containsKey(transaction.creditAccount)) {
        Account acc = accounts.get(transaction.creditAccount);
        acc.creditCount += 1;
        acc.balance += transaction.amount;
      } else {
        Account acc = new Account(
            transaction.creditAccount,
            0,
            1,
            transaction.amount);
        accounts.put(transaction.creditAccount, acc);
      }
    });

    return new ArrayList<>(accounts.values());
  }
}