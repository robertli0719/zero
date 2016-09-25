/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import java.util.List;
import robertli.zero.entity.TestRecord;

/**
 * JUnit can use this TestRecodeDao for system self-checking
 *
 * @version 1.0 2016-09-19
 * @author Robert Li
 */
public interface TestRecodeDao extends GenericDao<TestRecord, String> {

    /**
     * test to success save a list
     *
     * @param testRecodeList
     */
    public void testA(List<TestRecord> testRecodeList);

    /**
     * throws RuntimeException after save a list <br>
     * test rollback
     *
     * @param testRecodeList
     */
    public void testB(List<TestRecord> testRecodeList);

}
