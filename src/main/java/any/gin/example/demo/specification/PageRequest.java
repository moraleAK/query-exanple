package any.gin.example.demo.specification;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * User: Rolandz
 * Date: 7/12/16
 * Time: 8:40 AM
 */
public class PageRequest implements Pageable {
    private int rows = 15;

    private int page = 1;

    private String sortProperty = "id";

    private Sort.Direction sortDirection = Sort.Direction.DESC;

    public PageRequest() {
    }

    public PageRequest(int rows, int page) {
        this.rows = rows;
        this.page = page;
    }

    public PageRequest(int rows, int page, String sortProperty, Sort.Direction sortDirection) {
        this.rows = rows;
        this.page = page;
        this.sortProperty = sortProperty;
        this.sortDirection = sortDirection;
    }

    @SpecificationIgnore
    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @SpecificationIgnore
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @SpecificationIgnore
    public String getSortProperty() {
        return sortProperty;
    }

    public void setSortProperty(String sortProperty) {
        this.sortProperty = sortProperty;
    }

    @SpecificationIgnore
    public Sort.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }

    public  org.springframework.data.domain.PageRequest toPageable(){
        if(this.rows <= 0){
            this.rows = 15;
        }

        if(this.page <= 0){
            this.page = 1;
        }

        if(StringUtils.isEmpty(this.sortProperty)){
            this.sortProperty = "id";
            this.sortDirection = Sort.Direction.DESC;
        }
        return org.springframework.data.domain.PageRequest.of(this.page - 1 , this.rows, this.sortDirection, this.sortProperty);

    }

    public boolean isDeleted(){
        return false;
    }


    @Override
    @SpecificationIgnore
    public int getPageNumber() {
        return 0;
    }

    @Override
    @SpecificationIgnore
    public int getPageSize() {
        return 0;
    }

    @Override
    @SpecificationIgnore
    public long getOffset() {
        return 0;
    }

    @Override
    @SpecificationIgnore
    public Sort getSort() {
        return null;
    }

    @Override
    @SpecificationIgnore
    public Pageable next() {
        return null;
    }

    @Override
    @SpecificationIgnore
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    @SpecificationIgnore
    public Pageable first() {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    @SpecificationIgnore
    public boolean isPaged() {
        return false;
    }

    @Override
    @SpecificationIgnore
    public boolean isUnpaged() {
        return false;
    }

    @Override
    @SpecificationIgnore
    public Sort getSortOr(Sort sort) {
        return null;
    }

    @Override
    public Optional<Pageable> toOptional() {
        return Optional.empty();
    }
}
