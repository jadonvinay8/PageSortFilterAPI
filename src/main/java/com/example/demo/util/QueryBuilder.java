package com.example.demo.util;

import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor
public class QueryBuilder {

    private final StringBuilder query = new StringBuilder("select *, count(*) OVER() AS total_rows from ticket ");

    public QueryBuilder withUserId(String userId) {
        query.append("where user_id = ")
          .append(userId);
        return this;
    }

    public QueryBuilder withFiltering(Optional<List<String>> domains, Optional<List<String>> statuses) {
        domains.ifPresent(domainList -> query.append(" and domain IN ").append(buildSqlList(domainList)));
        statuses.ifPresent(statusList -> query.append(" and status IN ").append(buildSqlList(statusList)));
        return this;
    }

    private StringBuilder buildSqlList(List<String> list) {
        StringBuilder sqlList = new StringBuilder("('");
        sqlList.append(String.join("','", list)).append("')");
        return sqlList;
    }

    public QueryBuilder withSorting(List<SortObj> sorts) {
        if (sorts == null) {
            return this;
        }
        String sortingInfo = sorts.stream()
          .map(sort -> sort.getField() + " " + (sort.isAscending() ? "ASC" : "DESC"))
          .collect(Collectors.joining(", "));
        query.append(" order by ").append(sortingInfo);
        return this;
    }

    public QueryBuilder withPaging(int pageNum, int pageSize) {
        query.append(" limit ").append(pageSize)
          .append(" offset ").append((pageNum - 1) * pageSize);
        return this;
    }

    public String build() {
        return this.query.toString();
    }

}
