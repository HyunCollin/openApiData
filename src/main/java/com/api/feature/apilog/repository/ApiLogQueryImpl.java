package com.api.feature.apilog.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ApiLogQueryImpl implements ApiLogQuery {

    private final JPAQueryFactory jpaQueryFactory;

}
