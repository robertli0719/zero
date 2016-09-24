/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.dao;

import java.util.List;
import robertli.zero.entity.FileRecord;

/**
 *
 * @author Robert Li
 */
public interface FileRecordDao extends GenericDao<FileRecord, String> {

    public FileRecord saveFileRecord(String name, String type);

    /**
     * list the FileRecord which has been mark removed.
     *
     * @param lifeMinute
     * @return List of FileRecord
     */
    public List<FileRecord> listOverdueFileRecord(final int lifeMinute);
}
