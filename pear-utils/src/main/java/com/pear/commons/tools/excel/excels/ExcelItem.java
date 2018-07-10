package com.pear.commons.tools.excel.excels;

/**
 * 一个单元,包含单元格的跨度以及内容
 * Created by wangj on 2015/11/27.
 */
public class ExcelItem {
    /**
     * 单元格内容
     */
    private String itemValue;

    /**
     * 列偏移量
     */
    private int colOffset = 1;


    public ExcelItem(String itemValue, int colOffset) {
        this.itemValue = itemValue;
        this.colOffset = colOffset;
    }

    public ExcelItem(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }


    public int getColOffset() {
        return colOffset;
    }

    public void setColOffset(int colOffset) {
        this.colOffset = colOffset;
    }
}
