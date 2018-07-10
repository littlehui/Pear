package com.pear.commons.tools.excel.excels;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * User: littlehui
 * Date: 15-6-24
 * Time: 上午9:17
 */
public class ExcelUtil {

    /**
     * @param path            文件路径
     * @param cellCountPerRow 每行列数
     * @return
     */
    public static ExcelResult readExcelByPath(String path, int cellCountPerRow, int startRow) {
        XSSFWorkbook xwb = null;
        try {
            OPCPackage pkg = OPCPackage.open(path);
            xwb = new XSSFWorkbook(pkg);
            pkg.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readExcelByPath(xwb , cellCountPerRow , startRow);
    }

    public static ExcelResult readExcelFromFile(File file, int cellCountPerRow, int startRow) {
        XSSFWorkbook xwb = null;
        try {
            OPCPackage pkg = OPCPackage.openOrCreate(file);
            xwb = new XSSFWorkbook(pkg);
            pkg.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readExcelByPath(xwb , cellCountPerRow , startRow);
    }


    public static ExcelResult readExcelByPath(XSSFWorkbook xwb, int cellCountPerRow, int startRow) {
        // 读取第一章表格内容
        XSSFSheet sheet = xwb.getSheetAt(0);
        if (sheet == null) {
            throw new RuntimeException("excel的第一个tab页为空");
        }
        // 定义 row、cell
        XSSFRow row;
        int rowCount = 0;
        int realRowIndex = 0;
        String[][] excelValues = new String[sheet.getPhysicalNumberOfRows() - startRow + 1][cellCountPerRow];
        // 循环输出表格中的内容
        for (int i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++) {
            //获取行
            if (i < (startRow - 1)) {
                continue;
            }
            row = sheet.getRow(i);
            for (int cellIndex = 0; cellIndex < cellCountPerRow; cellIndex++) {
                String cellValue = "";
                if (row == null) {
                    excelValues[realRowIndex][cellIndex] = "";
                } else {
                    if (row.getCell(cellIndex) != null) {
                        row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
                        cellValue = row.getCell(cellIndex).getRichStringCellValue().getString();
                    }
                    excelValues[realRowIndex][cellIndex] = cellValue;
                }
            }
            realRowIndex++;
            rowCount++;
        }
        ExcelResult result = new ExcelResult(excelValues, rowCount, cellCountPerRow);
        return result;
    }

    public static String[] objectToArrays(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        List<String> values = new ArrayList();
        for (Field field : fields) {
            // 拿到该属性的getset方法
            Method m;
            try {
                m = obj.getClass().getMethod(
                        "get" + getMethodName(field.getName()));
                @SuppressWarnings("RedundantCast")
                Object val = (Object) m.invoke(obj);
                if (val == null) {
                    values.add("");
                } else {
                    values.add(val + "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return values.toArray(new String[values.size()]);
    }
    private static String getMethodName(String fildeName) throws Exception{
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }
}
