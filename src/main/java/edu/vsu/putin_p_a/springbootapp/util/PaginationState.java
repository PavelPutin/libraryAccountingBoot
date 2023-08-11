package edu.vsu.putin_p_a.springbootapp.util;

public class PaginationState {
    private int current;
    private int pageSize;
    private long total;

    public static PaginationState init(long total) {
        PaginationState p = new PaginationState();
        p.update(total, 0, 5);
        return p;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current < 0) { current = 0; }
        if (current >= getPagesAmount()) { current = getPagesAmount() - 1; }
        this.current = current;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize < 0) { pageSize = 5; }
        this.pageSize = pageSize;
    }

    public int getPagesAmount() {
        return (int) Math.ceil((double) total / pageSize);
    }

    public void update(long total, int current, int booksPerPage) {
        setTotal(total);
        setCurrent(current);
        setPageSize(booksPerPage);
    }
}
