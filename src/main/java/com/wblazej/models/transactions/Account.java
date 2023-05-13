package com.wblazej.models.transactions;

public class Account {
  public String account;
  public int debitCount;
  public int creditCount;
  public double balance;

  public Account(String account, int debitCount, int creditCount, double balance) {
    this.account = account;
    this.debitCount = debitCount;
    this.creditCount = creditCount;
    this.balance = balance;
  }

  public String getAccount() {
    return this.account;
  }
}
