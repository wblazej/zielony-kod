package com.wblazej.services;

import java.util.Comparator;
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
        acc.balance = Math.round(acc.balance * 100) / 100.0;
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
        acc.balance = Math.round(acc.balance * 100) / 100.0;
      } else {
        Account acc = new Account(
            transaction.creditAccount,
            0,
            1,
            transaction.amount);
        accounts.put(transaction.creditAccount, acc);
      }
    });

    return accounts.values()
        .stream()
        .sorted(Comparator.comparing(Account::getAccount))
        .toList();
  }
}