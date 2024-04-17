package com.jsonproject.finder.xml;
import com.jsonproject.finder.statistic.Statistic;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.*;


@NoArgsConstructor
@XmlRootElement(name = "statistics")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlStatistic {

    @XmlElement(name = "item")
    private final List<StatisticItem> items = new ArrayList<>();


    public XmlStatistic(Statistic statistic) {
        Map<String, Integer> stats = statistic.getStats();
        addAll(stats);
    }

    public void addAll(Map<String, Integer> stats) {
        stats.forEach((key, value) -> items.add(new StatisticItem(key, value)));
        sortItems();
    }

    public void sortItems(){
        Collections.sort(items);
    }

}

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
class StatisticItem implements Comparable<StatisticItem>{
    private String value;
    private Integer count;

    public StatisticItem() { }

    public StatisticItem(String value, Integer count) {
        this.value = value;
        this.count = count;
    }

    @Override
    public int compareTo(StatisticItem o) {
        return o.count - this.count;
    }


}