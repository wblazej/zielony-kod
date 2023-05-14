package com.wblazej.services;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.wblazej.models.atms.ATM;

public class ATMService {
  public static List<ATM> sort(List<ATM> data) {
    Map<String, ATM> atm_map = new LinkedHashMap<>();

    data.forEach(atm -> {
      String id = atm.region + "-" + atm.atmId;

      if (atm_map.containsKey(id) && atm.getPriority() <= atm_map.get(id).getPriority()) {
        return;
      }

      atm_map.put(id, atm);
    });

    return atm_map.values().stream()
        .sorted(
            Comparator.comparing(ATM::getRegion)
                .thenComparing(ATM::getPriority, Comparator.reverseOrder()))
        .collect(Collectors.toList());
  }
}
