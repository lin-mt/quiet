package cn.linmt.quiet.jpa.utils;

import cn.linmt.quiet.jpa.entity.QuietEntity;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.validation.constraints.NotNull;

/**
 * 查询条件构造器.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public abstract class SelectBuilder<T extends Predicate> {

  @NotNull
  public static WhereBuilder booleanBuilder() {
    return new WhereBuilder();
  }

  @NotNull
  public static WhereBuilder booleanBuilder(QuietEntity quietEntity) {
    com.querydsl.core.BooleanBuilder builder = null;
    if (quietEntity != null) {
      builder = quietEntity.booleanBuilder();
    }
    return new WhereBuilder(builder);
  }

  /**
   * 获取查询条件
   *
   * @return 查询条件
   */
  @NotNull
  public abstract T getPredicate();

  public <E> JPAQuery<E> from(
      @NotNull JPAQueryFactory jpaQueryFactory, @NotNull EntityPath<E> from) {
    Predicate predicate = getPredicate();
    if (predicate == null) {
      throw new IllegalStateException("SelectBuilder 子类实现的方法不能返回 null");
    }
    return jpaQueryFactory.selectFrom(from).where(predicate);
  }
}
