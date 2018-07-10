package com.pear.commons.tools.excel.excels.excption;

/**
 * User: littlehui
 * Date: 2015/12/14
 * Time: 15:33
 */
public class NonLoadException extends Exception {
    public NonLoadException() {
        super("数据量为0，导出失败。");
    }
}
