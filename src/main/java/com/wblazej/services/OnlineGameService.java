package com.wblazej.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.wblazej.models.onlinegame.Clan;
import com.wblazej.models.onlinegame.Request;

public class OnlineGameService {
  public static List<List<Clan>> process_queue(Request data) {
    List<Clan> clans = data.clans.stream().sorted(
        Comparator.comparing(Clan::getPoints, Comparator.reverseOrder())
            .thenComparing(Clan::getNumberOfPlayers))
        .toList();

    int player_count = clans.stream().map(Clan::getNumberOfPlayers).reduce(0, Integer::sum);
    int groups_count = (int) Math.ceil((double) player_count / data.groupCount);

    List<List<Clan>> groups = Stream.generate(() -> (List<Clan>) new ArrayList<Clan>()).limit(groups_count)
        .collect(Collectors.toList());

    int[] sums = new int[groups_count];
    List<Clan> leftovers = new ArrayList<>();

    clans.forEach(clan -> {
      for (int i = 0; i < groups_count; i++) {
        if (sums[i] + clan.getNumberOfPlayers() > data.groupCount) {
          continue;
        }

        sums[i] += clan.getNumberOfPlayers();
        groups.get(i).add(clan);
        return;
      }

      leftovers.add(clan);
    });

    if (leftovers.size() > 0) {
      Request req = new Request(
        data.groupCount,
        leftovers
      );

      groups.addAll(OnlineGameService.process_queue(req));
    }

    return groups;
  }
}
