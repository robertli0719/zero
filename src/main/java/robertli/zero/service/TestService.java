/*
 * The MIT License
 *
 * Copyright 2016 Robert Li.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package robertli.zero.service;

import java.util.List;
import robertli.zero.entity.TestRecord;

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

    public List<TestRecord> getList();

    public void save(TestRecord test);

    public void save(List<TestRecord> testList);

    public void get(String key);

    public void deleteByKey(String key);

    /**
     * test1 use session to save test directly.<br >
     * should save success
     *
     * @param testList
     */
    public void test1(List<TestRecord> testList);

    /**
     * test2 use session to save test directly,than throw RuntionException <br >
     * should save fail
     *
     * @param testList
     */
    public void test2(List<TestRecord> testList);

    /**
     * test3 invoke test1 to save it <br >
     * should save success
     *
     * @param testList
     */
    public void test3(List<TestRecord> testList);

    /**
     * test4 invoke test1 to save it, than throw RuntionException <br >
     * should save fail
     *
     * @param testList
     */
    public void test4(List<TestRecord> testList);

    /**
     * test5 use test2 to save it.<br>
     * should save fail
     *
     * @param testList
     */
    public void test5(List<TestRecord> testList);

    /**
     * test6 use DAO.testA success save, than throw RuntimeException<br>
     * should save fail
     *
     * @param testList
     */
    public void test6(List<TestRecord> testList);

    /**
     * test7 use DAO.testA success save, than use DAO.testB to throw
     * RuntimeException<br>
     * should save fail
     *
     * @param testList
     */
    public void test7(List<TestRecord> testList);

    /**
     * test8 use test1 to success save, than use DAO.testB to throw
     * RuntimeException<br>
     * should save fail
     *
     *
     * @param testList
     */
    public void test8(List<TestRecord> testList);
}
