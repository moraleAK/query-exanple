package any.gin.template.common.jpa.extend;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * User: Rolandz Date: 7/12/16 Time: 8:44 AM
 */
public class PaginationResult<T, R> {
    private long total;
    private int totalPages;
    private List<R> rows;

    public PaginationResult(long total, int totalPages, List<R> rows) {
        this.total = total;
        this.totalPages = totalPages;
        this.rows = rows;
    }

    public PaginationResult() {
    }

    PaginationResult of(Page<T> page, Function<T, R> transferData) {
        this.rows = page.getContent().stream().map(transferData).collect(Collectors.toList());
        this.total = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        return this;
    }

    public long getTotal() {
        return total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<R> getRows() {
        return rows;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setRows(List<R> rows) {
        this.rows = rows;
    }

    public static PaginationResult init(Page page, List rows) {
        return new PaginationResult(page.getTotalElements(), page.getTotalPages(), rows);
    }

    public static PaginationResult init(long total, int totalPages, List rows) {
        return new PaginationResult(total, totalPages, rows);
    }

}
