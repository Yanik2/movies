package com.test.movie.dao;

import com.test.movie.model.Order;
import com.test.movie.util.SearchCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification implements Specification<Order> {

    private SearchCriteria criteria;

    public OrderSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public SearchCriteria getCriteria() {
        return criteria;
    }

    public void setCriteria(SearchCriteria criteria) {
        this.criteria = criteria;
    }
    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(
                root.get(criteria.getKey()), criteria.getValue());
        }
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
                root.get(criteria.getKey()), criteria.getValue());
        }
        else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                    root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()).get("id"), criteria.getValue());
            }
        }
        return null;
    }
}
