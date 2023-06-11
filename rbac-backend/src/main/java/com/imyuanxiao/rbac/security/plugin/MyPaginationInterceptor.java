package com.imyuanxiao.rbac.security.plugin;

import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.imyuanxiao.rbac.model.vo.UserDetailsVO;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 该拦截器用于数据权限控制，拦截查询数据语句，增加筛选条件org_id
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Slf4j
public class MyPaginationInterceptor implements InnerInterceptor {

    @Override
    public void beforePrepare(StatementHandler statementHandler, Connection connection, Integer transactionTimeout) {
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        // id为执行的mapper方法的全路径名，如com.imyuanxiao.rbac.mapper.selectPage
        String id = mappedStatement.getId();
        log.info("mapper: ==> {}", id);
        // 如果不是指定的方法，直接结束拦截
        // 如果方法多可以存到一个集合里，然后判断当前拦截的是否存在集合中
        if (!id.startsWith("com.imyuanxiao.rbac.mapper.DataMapper.selectPage")) {
            return;
        }

        // 获取到原始sql语句
        String sql = statementHandler.getBoundSql().getSql();
        log.info("原始SQL语句： ==> {}", sql);
        sql = getSql(sql);
        // 修改sql
        metaObject.setValue("delegate.boundSql.sql", sql);
        log.info("拦截后SQL语句：==>{}", sql);
    }

    /**
     * 解析SQL语句，并返回新的SQL语句
     *
     * @param sql 原SQL
     * @return 新SQL
     */
    private String getSql(String sql) {
        try {
            // 解析语句
            Statement stmt = CCJSqlParserUtil.parse(sql);
            Select selectStatement = (Select) stmt;
            PlainSelect ps = (PlainSelect) selectStatement.getSelectBody();
            // 拿到表信息
            FromItem fromItem = ps.getFromItem();
            Table table = (Table) fromItem;
            String mainTable = table.getAlias() == null ? table.getName() : table.getAlias().getName();

            List<Join> joins = ps.getJoins();
            if (joins == null) {
                joins = new ArrayList<>(1);
            }

            // 创建连表join条件
            Join join = new Join();
            join.setInner(true);
            join.setRightItem(new Table("user_organization uo"));
            // 第一个：两表通过company_id连接
            EqualsTo joinExpression = new EqualsTo();
            joinExpression.setLeftExpression(new Column(mainTable + ".org_id"));
            joinExpression.setRightExpression(new Column("uo.org_id"));
            // 第二个条件：和当前登录用户id匹配
            EqualsTo userIdExpression = new EqualsTo();
            userIdExpression.setLeftExpression(new Column("uo.user_id"));
            // 拿到当前用户
            UserDetailsVO user = (UserDetailsVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userIdExpression.setRightExpression(new LongValue(user.getUser().getId()));
            // 将两个条件拼接起来
            join.setOnExpression(new AndExpression(joinExpression, userIdExpression));
            joins.add(join);
            ps.setJoins(joins);

            // 修改原语句
            sql = ps.toString();
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        return sql;
    }
}