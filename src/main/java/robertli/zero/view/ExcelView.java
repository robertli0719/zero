/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.view;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

/**
 * This is an example to make a excel view
 * @author Robert Li
 */
@Component("excelView")
public class ExcelView extends AbstractXlsxView {
    
    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook wrkbk, HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        Sheet sheet = wrkbk.createSheet();
        
        
        for (int i = 1; i < 100; i++) {
            Row row = sheet.createRow(i);
            for (int z = 1; z < 10; z++) {
                Cell cell = row.createCell(z, CellType.NUMERIC);
                cell.setCellValue(i * z * 0.123);
            }
        }
    }
    
}
