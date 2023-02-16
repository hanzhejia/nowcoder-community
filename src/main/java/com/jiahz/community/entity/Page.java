package com.jiahz.community.entity;

/**
 * ClassName: Page
 *
 * @Author: jiahz
 * @Date: 2023/1/19 16:38
 * @Description: 封装分页相关信息
 */
public class Page {
    // 当前页码
    private int current = 1;
    // 一页放多少条数据
    private int size = 10;
    // 数据总数
    private int rows;

    //查询路径
    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current >= 1) {
            this.current = current;
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        if (size >= 1 && size <= 100) {
            this.size = size;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if (rows >= 0) {
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    /**
     * 获取当前页的起始行
     *
     * @return
     */
    public int getOffset() {
        return (current - 1) * size;
    }

    /**
     * 获取总页数
     *
     * @return
     */
    public int getTotal() {
        if (rows % size == 0) {
            return rows / size;
        } else {
            return rows / size + 1;
        }
    }

    /**
     * 获取起始页码
     *
     * @return
     */
    public int getFrom() {
        int from = current - 2;
        return from < 1 ? 1 : from;
    }

    /**
     * 获取结束页码
     *
     * @return
     */
    public int getTo() {
        int to = current + 2;
        int total = getTotal();
        return to > total ? total : to;
    }
}
