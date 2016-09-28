/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.model;

import java.util.List;

/**
 * This is the model for a HQL search result with paging
 *
 * @version 1.0 2016-09-27
 * @author Robert Li
 * @param <T> The Entity Class
 */
public class SearchResult<T> {

    private final int start;
    private final int max;
    private final int count;
    private final List<T> list;

    public SearchResult(int start, int max, int count, List<T> list) {
        this.start = start;
        this.max = max;
        this.count = count;
        this.list = list;
    }

    public int getStart() {
        return start;
    }

    public int getMax() {
        return max;
    }

    public int getCount() {
        return count;
    }

    public int getPageId() {
        return start / max + 1;
    }

    public int getPageSize() {
        return count / max + 1;
    }

    public List<T> getList() {
        return list;
    }

}
