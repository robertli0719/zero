/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.struts2;

/**
 * The struts2 Actions which implements FileResultSupport will support
 * FileResult.
 *
 * @see FileResult
 * @version 1.0 2016-09-29
 * @author Robert Li
 */
public interface FileResultSupport {

    public static final String FILE = "file";

    public byte[] getFileInBytes();

    public String getContentType();
}
