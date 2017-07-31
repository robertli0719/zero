/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core.impl;

import java.security.SecureRandom;
import org.springframework.stereotype.Component;
import robertli.zero.core.RandomCodeCreater;

/**
 * RandomCodeCreater can create random string
 *
 * @version 1.0.2 2017-07-05
 * @author Robert Li
 */
@Component("randomCodeCreater")
public class RandomCodeCreaterImpl implements RandomCodeCreater {

    private final String NUMBERS = "0123456789";
    private final String UPPER_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String LOWER_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private final String BINARY = "01";
    private final String OCTAL = "01234567";
    private final String HEXADECIMAL = "0123456789ABCDEF";
    private final String LETTERS = LOWER_LETTERS + UPPER_LETTERS;
    private final String MIX = LETTERS + NUMBERS;
    private final String LOWER_LETTERS_AND_NUMBERS = LOWER_LETTERS + NUMBERS;

    private String getPatternString(CodeType type) {
        if (null != type) {
            switch (type) {
                case NUMBERS:
                    return NUMBERS;
                case BINARY:
                    return BINARY;
                case OCTAL:
                    return OCTAL;
                case HEXADECIMAL:
                    return HEXADECIMAL;
                case UPPER_LETTERS:
                    return UPPER_LETTERS;
                case LOWER_LETTERS:
                    return LOWER_LETTERS;
                case LETTERS:
                    return LETTERS;
                case MIX:
                    return MIX;
                case LOWER_LETTERS_AND_NUMBERS:
                    return LOWER_LETTERS_AND_NUMBERS;
                default:
                    throw new RuntimeException("RandomCodeCreater can't support the CodeType:" + type);
            }
        }
        return null;
    }

    @Override
    public String createRandomCode(int size, CodeType type) {
        StringBuilder sb = new StringBuilder();

        SecureRandom rand = new SecureRandom();
        final String patternStr = getPatternString(type);
        for (int i = 0; i < size; i++) {
            int index = rand.nextInt(patternStr.length());
            sb.append(patternStr.charAt(index));
        }
        return sb.toString();
    }

}
