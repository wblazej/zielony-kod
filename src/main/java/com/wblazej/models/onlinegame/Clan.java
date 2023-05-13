package com.wblazej.models.onlinegame;

public class Clan {
  public int numberOfPlayers;
  public int points;

  public Clan(int numberOfPlayers, int points) {
    this.numberOfPlayers = numberOfPlayers;
    this.points = points;
  }

  public int getNumberOfPlayers() {
    return numberOfPlayers;
  }

  public int getPoints() {
    return points;
  }
}
