/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
