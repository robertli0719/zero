/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core;

/**
 * RandomCodeCreater can create random string
 *
 * @version 1.0.1 2017-07-05
 * @author Robert Li
 */
public interface RandomCodeCreater {

    public enum CodeType {
        NUMBERS, UPPER_LETTERS, LOWER_LETTERS, LETTERS, MIX,
        BINARY, OCTAL, HEXADECIMAL, LOWER_LETTERS_AND_NUMBERS
    }

    public String createRandomCode(int size, CodeType type);

}
