/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @version 1.0 2017-03-27
 * @author Robert Li
 */
public class PagingModal {

    public final static int MAX_LIMIT = 100;
    public final static int DEFAULT_LIMIT = 20;

    private int count;
    private final int offset;
    private int limit = DEFAULT_LIMIT;
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public PagingModal(HttpServletRequest request, HttpServletResponse response, int limit, int offset) {
        this.limit = limit;
        this.offset = offset;
        this.request = request;
        this.response = response;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    private void placePaginationHeaders() {
        response.setHeader("X-Pagination-Count", "" + count);
        response.setHeader("X-Pagination-Limit", "" + limit);
        response.setHeader("X-Pagination-Offset", "" + offset);
    }

    private void addLink(HttpServletResponse response, String url, String rel) {
        final String line = " <" + url + ">; rel=\"" + rel + "\"";
        String header = response.getHeader("Link");
        if (header == null) {
            header = line;
        } else {
            header += "," + line;
        }
        response.setHeader("Link", header);
    }

    private void placeLinkHeaders() {
        final int nextOffset = offset + limit;
        final int prevOffset = offset - limit;
        final int appenedPageNumber = (count % limit) > 0 ? 1 : 0;
        final int lastOffset = ((count / limit) + appenedPageNumber - (count > 0 ? 1 : 0)) * limit;
        final String url = request.getRequestURL().toString();

        final String selfUrl = url + "?offset=" + offset + "&limit=" + limit;
        final String firstUrl = url + "?offset=0&limit=" + limit;
        final String prevUrl = url + "?offset=" + prevOffset + "&limit=" + limit;
        final String nextUrl = url + "?offset=" + nextOffset + "&limit=" + limit;
        final String lastUrl = url + "?offset=" + lastOffset + "&limit=" + limit;

        addLink(response, selfUrl, "self");
        if (offset > 0) {
            addLink(response, firstUrl, "first");
        }
        if (prevOffset >= 0) {
            addLink(response, prevUrl, "prev");
        }
        if (nextOffset < count) {
            addLink(response, nextUrl, "next");
            addLink(response, lastUrl, "last");
        }
    }

    public void placeHeaders(int count) {
        this.count = count;
        placePaginationHeaders();
        placeLinkHeaders();
    }

    @Override
    public String toString() {
        return "PagingModal{" + "count=" + count + ", offset=" + offset + ", limit=" + limit + '}';
    }

}
