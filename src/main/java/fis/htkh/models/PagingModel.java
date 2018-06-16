package fis.htkh.models;

public class PagingModel {
    private int pageIndex;
    private int pageSize;
    private String key;
    private String column;
    private Integer desending;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Integer getDesending() {
        return desending;
    }

    public void setDesending(Integer desending) {
        this.desending = desending;
    }
}
