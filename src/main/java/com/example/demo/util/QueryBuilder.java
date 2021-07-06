package com.example.demo.util;

import com.example.demo.domain.Dates;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public QueryBuilder withFiltering(Optional<List<String>> domains, Optional<List<String>> statuses, Dates dates) {
        domains.ifPresent(domainList -> query.append(" and domain IN ").append(buildSqlList(domainList)));
        statuses.ifPresent(statusList -> query.append(" and status IN ").append(buildSqlList(statusList)));
        filterOnDates(dates);
        return this;
    }

    private void filterOnDates(Dates dates) {
        if (dates.getCreationStartDate() != null) {
            Timestamp timestamp = getTimestamp(dates.getCreationStartDate());
            query.append(" and creation_time > ").append("'").append(timestamp).append("'");
        }
        if (dates.getCreationEndDate() != null) {
            Timestamp timestamp = getTimestamp(dates.getCreationEndDate());
            query.append(" and creation_time < ").append("'").append(timestamp).append("'");
        }
        if (dates.getClosureStartDate() != null) {
            Timestamp timestamp = getTimestamp(dates.getClosureStartDate());
            query.append(" and closure_time > ").append("'").append(timestamp).append("'");
        }
        if (dates.getClosureEndDate() != null) {
            Timestamp timestamp = getTimestamp(dates.getClosureEndDate());
            query.append(" and closure_time < ").append("'").append(timestamp).append("'");
        }
    }

    private Timestamp getTimestamp(String creationStartDate) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Timestamp creationStartTimestamp;
        try {
            Date date = format.parse(creationStartDate);
            creationStartTimestamp = new Timestamp(date.getTime());
        } catch (ParseException e) {
            throw new RuntimeException("parsing issue", e);
        }
        return creationStartTimestamp;
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
