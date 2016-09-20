/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.dao;

import java.util.List;
import robertli.zero.entity.TestRecode;

/**
 * JUnit can use this TestRecodeDao for system self-checking
 *
 * @version 1.0 2016-09-19
 * @author Robert Li
 */
public interface TestRecodeDao extends GenericDao<TestRecode, String> {

    /**
     * test to success save a list
     *
     * @param testRecodeList
     */
    public void testA(List<TestRecode> testRecodeList);

    /**
     * throws RuntimeException after save a list <br>
     * test rollback
     *
     * @param testRecodeList
     */
    public void testB(List<TestRecode> testRecodeList);

}
