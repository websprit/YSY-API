package cn.ysy.open.response;

import com.alibaba.fastjson2.annotation.JSONField;

/**
 */
public class PageResponse {

    @JSONField(name="total")
    public int total;
    @JSONField(name="page")
    public int page;
    @JSONField(name="size")
    public int size;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
