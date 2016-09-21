/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.service;

import java.util.List;
import robertli.zero.entity.TestRecode;

/**
 * JUnit can use this service for system self-checking
 *
 * I am writing this service for checking if the spring transaction manager
 * working correctly. This service will be useful when we change the
 * configuration in the spring XML file.
 *
 * @version 1.0 2016-09-19
 * @author Robert Li
 */
public interface TestService {

    public void clearAll();

    public List<TestRecode> getList();

    public void save(TestRecode test);

    public void save(List<TestRecode> testList);

    public void get(String key);

    public void deleteByKey(String key);

    /**
     * test1 use session to save test directly.<br >
     * should save success
     *
     * @param testList
     */
    public void test1(List<TestRecode> testList);

    /**
     * test2 use session to save test directly,than throw RuntionException <br >
     * should save fail
     *
     * @param testList
     */
    public void test2(List<TestRecode> testList);

    /**
     * test3 invoke test1 to save it <br >
     * should save success
     *
     * @param testList
     */
    public void test3(List<TestRecode> testList);

    /**
     * test4 invoke test1 to save it, than throw RuntionException <br >
     * should save fail
     *
     * @param testList
     */
    public void test4(List<TestRecode> testList);

    /**
     * test5 use test2 to save it.<br>
     * should save fail
     *
     * @param testList
     */
    public void test5(List<TestRecode> testList);

    /**
     * test6 use DAO.testA success save, than throw RuntimeException<br>
     * should save fail
     *
     * @param testList
     */
    public void test6(List<TestRecode> testList);

    /**
     * test7 use DAO.testA success save, than use DAO.testB to throw
     * RuntimeException<br>
     * should save fail
     *
     * @param testList
     */
    public void test7(List<TestRecode> testList);

    /**
     * test8 use test1 to success save, than use DAO.testB to throw
     * RuntimeException<br>
     * should save fail
     *
     *
     * @param testList
     */
    public void test8(List<TestRecode> testList);
}