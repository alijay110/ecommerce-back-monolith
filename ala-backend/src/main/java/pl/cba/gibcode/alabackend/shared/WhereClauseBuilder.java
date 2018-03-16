
package pl.cba.gibcode.alabackend.shared;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.Function;

public class WhereClauseBuilder {

/** Boolean Builder */

    private final BooleanBuilder builder;

    public WhereClauseBuilder(Predicate predicate) {
        this.builder = new BooleanBuilder(predicate);
    }


/**
     * Returns new where clause with chained right statement.
     *
     * @param right predicate to chain
     * @return builder with chained statement
     */

    public WhereClauseBuilder and(Predicate right) {
        return new WhereClauseBuilder(builder.and(right));
    }


/**
     * Returns new where clause with chained and statement for Collection only if Collection is not empty
     *
     * @param expressionFunction BooleanExpression function to evaluate if Collection is not empty
     * @param value collection to use in BooleanExpression
     * @param <T> type of collection
     * @return builder with chained statement or current if collection was null or empty
     */

    public <T> WhereClauseBuilder notEmptyAnd(Function<Collection<T>, BooleanExpression> expressionFunction, @Nullable Collection<T> value) {
        return CollectionUtils.isNotEmpty(value) ? and(expressionFunction.apply(value)) : this;
    }


/**
     * Returns new where clause with chained and statement for Object only if Object is not null
     *
     * @param expressionFunction BooleanExpression function to evaluate if Object is not null
     * @param value Object to use in BooleanExpression
     * @param <T> type of Object
     * @return builder with chained statement or current if Object was null
     */

    public <T> WhereClauseBuilder optionalAnd(Function<T, BooleanExpression> expressionFunction, @Nullable T value) {
        return (value != null) ? and(expressionFunction.apply(value)) : this;
    }


/**
     * Returns new where clause with chained and statement for String only if String is not empty
     *
     * @param expressionFunction BooleanExpression function to evaluate if String is not empty
     * @param value String to use in BooleanExpression
     * @return builder with chained statement or current if String was null or empty
     */

    public WhereClauseBuilder notEmptyAnd(Function<String, BooleanExpression> expressionFunction, @Nullable String value) {
        return StringUtils.isNotEmpty(value) ? and(expressionFunction.apply(value)) : this;
    }


/**
     * Build predicates of builder state
     *
     * @return predicate built of builder state
     */

    public Predicate build() {
        return builder;
    }

}