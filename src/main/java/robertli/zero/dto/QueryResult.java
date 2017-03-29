/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dto;

import java.util.List;

/**
 *
 * @version 1.0 2017-03-28
 * @author Robert Li
 */
public class QueryResult<T> {

    private final List<T> resultList;
    private final int count;
    private final int offset;
    private final int limit;

    public QueryResult(int offset, int limit, int count, List<T> resultList) {
        this.offset = offset;
        this.limit = limit;
        this.count = count;
        this.resultList = resultList;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public int getCount() {
        return count;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

}
