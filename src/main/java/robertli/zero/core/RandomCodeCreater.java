/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core;

/**
 * RandomCodeCreater can create random string
 *
 * @version 1.0 2016-09-19
 * @author Robert Li
 */
public interface RandomCodeCreater {

    public enum CodeType {
        NUMBERS, UPPER_LETTERS, LOWER_LETTERS, LETTERS, MIX
    }

    public String createRandomCode(int size, CodeType type);

}
