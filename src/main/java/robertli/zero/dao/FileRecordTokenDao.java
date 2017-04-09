/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import java.util.List;
import robertli.zero.entity.FileRecordToken;

/**
 *
 * @version 2017-03-15 1.0.0
 * @author Robert Li
 */
public interface FileRecordTokenDao extends GenericDao<FileRecordToken, String> {

    public FileRecordToken saveFileRecord(String name, String type, long length);

    /**
     * list the FileRecordToken which has been mark removed.
     *
     * @param lifeMinute the life time in minute
     * @return List of FileRecordToken
     */
    public List<FileRecordToken> listOverdueFileRecord(final int lifeMinute);
}
