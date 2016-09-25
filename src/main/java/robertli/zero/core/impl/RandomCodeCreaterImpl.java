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
package robertli.zero.core.impl;

import java.util.Random;
import org.springframework.stereotype.Component;
import robertli.zero.core.RandomCodeCreater;

/**
 * RandomCodeCreater can create random string
 *
 * @version 1.0 2016-09-19
 * @author Robert Li
 */
@Component("randomCodeCreater")
public class RandomCodeCreaterImpl implements RandomCodeCreater {

    private final String NUMBERS = "0123456789";
    private final String UPPER_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String LOWER_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private final String LETTERS = LOWER_LETTERS + UPPER_LETTERS;
    private final String MIX = LETTERS + NUMBERS;

    private String getPatternString(CodeType type) {
        if (null != type) {
            switch (type) {
                case NUMBERS:
                    return NUMBERS;
                case UPPER_LETTERS:
                    return UPPER_LETTERS;
                case LOWER_LETTERS:
                    return LOWER_LETTERS;
                case LETTERS:
                    return LETTERS;
                case MIX:
                    return MIX;
                default:
                    throw new RuntimeException("RandomCodeCreater can't support the CodeType:" + type);
            }
        }
        return null;
    }

    @Override
    public String createRandomCode(int size, CodeType type) {
        StringBuilder sb = new StringBuilder();

        Random rand = new Random();
        final String patternStr = getPatternString(type);
        for (int i = 0; i < size; i++) {
            int index = rand.nextInt(patternStr.length());
            sb.append(patternStr.charAt(index));
        }
        return sb.toString();
    }

}
