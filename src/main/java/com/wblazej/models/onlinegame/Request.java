package com.wblazej.models.onlinegame;

import java.util.List;

public class Request {
  public int groupCount;
  public List<Clan> clans;

  public Request(int groupCount, List<Clan> clans) {
    this.groupCount = groupCount;
    this.clans = clans;
  }
}
