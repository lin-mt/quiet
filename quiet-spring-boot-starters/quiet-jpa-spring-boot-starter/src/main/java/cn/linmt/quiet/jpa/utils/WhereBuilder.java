package cn.linmt.quiet.jpa.utils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.function.Consumer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 构建 BooleanBuilder.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class WhereBuilder extends SelectBuilder<BooleanBuilder> {

  private final BooleanBuilder builder;

  public WhereBuilder() {
    this.builder = new BooleanBuilder();
  }

  public WhereBuilder(BooleanBuilder builder) {
    this.builder = builder == null ? new BooleanBuilder() : builder;
  }

  @Override
  public BooleanBuilder getPredicate() {
    return builder;
  }

  public WhereBuilder and(Predicate right) {
    builder.and(right);
    return this;
  }

  public WhereBuilder andAnyOf(Predicate... args) {
    builder.andAnyOf(args);
    return this;
  }

  public WhereBuilder andNot(Predicate right) {
    return and(right.not());
  }

  public WhereBuilder or(Predicate right) {
    builder.or(right);
    return this;
  }

  public WhereBuilder orAllOf(Predicate... args) {
    builder.orAllOf(args);
    return this;
  }

  public WhereBuilder orNot(Predicate right) {
    return or(right.not());
  }

  public WhereBuilder notNullEq(Boolean param, BooleanPath path) {
    if (param != null) {
      builder.and(path.eq(param));
    }
    return this;
  }

  public <T extends Number & Comparable<?>> WhereBuilder notNullEq(T param, NumberPath<T> path) {
    if (param != null) {
      builder.and(path.eq(param));
    }
    return this;
  }

  public WhereBuilder isIdEq(Long param, NumberPath<Long> path) {
    if (param != null && param > 0L) {
      builder.and(path.eq(param));
    }
    return this;
  }

  public <T extends Number & Comparable<?>> WhereBuilder leZeroIsNull(T param, NumberPath<T> path) {
    if (param != null && param.longValue() <= 0) {
      builder.and(path.isNull());
    }
    return this;
  }

  public WhereBuilder notBlankEq(String param, StringPath path) {
    if (StringUtils.isNoneBlank(param)) {
      builder.and(path.eq(param));
    }
    return this;
  }

  public WhereBuilder with(@NotNull Consumer<WhereBuilder> consumer) {
    if (consumer != null) {
      consumer.accept(this);
    }
    return this;
  }

  public <T extends Enum<T>> WhereBuilder notNullEq(T param, EnumPath<T> path) {
    if (param != null) {
      builder.and(path.eq(param));
    }
    return this;
  }

  public WhereBuilder notBlankContains(String param, StringPath path) {
    if (StringUtils.isNoneBlank(param)) {
      builder.and(path.contains(param));
    }
    return this;
  }

  public WhereBuilder notNullBefore(LocalDateTime param, DateTimePath<LocalDateTime> path) {
    if (param != null) {
      builder.and(path.before(param));
    }
    return this;
  }

  public WhereBuilder notNullAfter(LocalDateTime param, DateTimePath<LocalDateTime> path) {
    if (param != null) {
      builder.and(path.after(param));
    }
    return this;
  }

  public WhereBuilder notEmptyIn(Collection<? extends Long> param, NumberPath<Long> path) {
    if (CollectionUtils.isNotEmpty(param)) {
      builder.and(path.in(param));
    }
    return this;
  }

  public WhereBuilder findInSet(Long param, SetPath<Long, NumberPath<Long>> path) {
    if (param != null) {
      builder.and(Expressions.booleanTemplate("FIND_IN_SET({0}, {1}) > 0", param, path));
    }
    return this;
  }
}
