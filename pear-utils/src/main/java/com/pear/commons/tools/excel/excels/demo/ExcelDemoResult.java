package com.pear.commons.tools.excel.excels.demo;

import com.pear.commons.tools.excel.excels.AbstractExcelResult;

import java.util.List;

/**
 * Created by littlehui on 2018/3/26.
 * @author littlehui
 */
public class ExcelDemoResult extends AbstractExcelResult<Demo> {

    public static String[] excelRowHeaders = {
            "序号", "编码"
    };

    public ExcelDemoResult(List<Demo> excelVos) {
        super(excelVos);
    }

    @Override
    public void initHeaders() {
        super.rowHeaders = excelRowHeaders;
    }
}
