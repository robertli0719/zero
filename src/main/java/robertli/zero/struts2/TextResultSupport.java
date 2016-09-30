/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.struts2;

/**
 * The struts2 Actions which implements TextResultSupport will support
 * TextResult.
 *
 * @see TextResult
 * @version 1.0 2016-09-29
 * @author Robert Li
 */
public interface TextResultSupport {

    public static final String TEXT = "text";

    public String getTextResult();
}
