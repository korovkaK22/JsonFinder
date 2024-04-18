package com.jsonproject.finder.xml;
import com.jsonproject.finder.statistic.Statistic;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.*;


/**
 * The XmlStatistic class represents a collection of statistical data that can be marshalled into XML format.
 */
@NoArgsConstructor
@XmlRootElement(name = "statistics")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlStatistic {

    /**
     * Stores statistic values in StatisticItem objects
     */
    @XmlElement(name = "item")
    @Getter
    private final List<StatisticItem> items = new ArrayList<>();

    /**
     * Constructs an XmlStatistic object using data from a given Statistic instance.
     * Converts each entry in the statistic map to an XML-serializable StatisticItem and sorts them.
     *
     * @param statistic the statistic object whose data will be converted to XML format.
     */
    public XmlStatistic(Statistic statistic) {
        Map<String, Integer> stats = statistic.getStatistic();
        addAll(stats);
    }

    /**
     * Adds all entries from a map of statistics to the list of StatisticItems.
     *
     * @param stats a map of statistics where each key is a value and each value is the count of occurrences.
     */
    public void addAll(Map<String, Integer> stats) {
        stats.forEach((key, value) -> items.add(new StatisticItem(key, value)));
        sortItems();
    }

    /**
     * Sorts the items in descending order based on the count of occurrences.
     */
    public void sortItems(){
        Collections.sort(items);
    }


    /**
     * Inner class representing an item in the statistic. Each item is comparable based on its count,
     * allowing for sorting then statistic by count.
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @Getter
    @Setter
   private static class StatisticItem implements Comparable<StatisticItem>{
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

}

