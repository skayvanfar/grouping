package ir.sk.processing;

import java.time.LocalDate;
import java.util.Comparator;

/**
 * Created by sad.kayvanfar on 11/14/2021.
 */
public interface TypeCompareStrategy {

    int compareTo(String s1, String s2);

    // Normal equal strategy
    static TypeCompareStrategy normalCompareStrategy() {
        return (s1, s2) ->  s1.compareTo(s2);
    }

    // Strategy for LocalDate
    static TypeCompareStrategy dateCompareStrategy() {
        return (s1, s2) -> LocalDate.parse(s1).compareTo(LocalDate.parse(s2));
    }
}
