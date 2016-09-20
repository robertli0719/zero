/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
