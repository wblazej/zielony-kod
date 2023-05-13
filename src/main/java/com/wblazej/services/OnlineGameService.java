package com.wblazej.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import com.wblazej.models.onlinegame.Clan;
import com.wblazej.models.onlinegame.Request;

public class OnlineGameService {
  public static List<List<Clan>> process_queue(Request data) {
    List<Clan> clans = data.clans.stream().sorted(
        Comparator.comparing(Clan::getPoints)
            .thenComparing(Clan::getNumberOfPlayers, Comparator.reverseOrder()))
        .toList();

    int groups_count = (int) Math
        .ceil(clans.stream().map(Clan::getNumberOfPlayers).reduce(0, Integer::sum) / data.groupCount);

    List<ArrayList<Clan>> groups = Stream.generate(() -> new ArrayList<Clan>()).limit(groups_count).toList();
    int[] sums = new int[groups_count];

    clans.forEach(clan -> {
      for (int i = 0; i < groups_count; i++) {
        if (sums[i] + clan.getNumberOfPlayers() > data.groupCount) {
          continue;
        }

        sums[i] += clan.getNumberOfPlayers();
        groups.get(i).add(clan);
        break;
      }
    });

    return groups.stream()
        .map(group -> group.stream().sorted(Comparator.comparing(Clan::getPoints).reversed()).toList()).toList();
  }
}
