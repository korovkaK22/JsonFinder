package utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapsCompare {
    public static boolean compare(Map<String, Integer> map1, Map<String, Integer> map2) {
        return map1.size() == map2.size() && map1.entrySet().stream()
                .allMatch(e -> e.getValue().equals(map2.get(e.getKey())));
    }



}
