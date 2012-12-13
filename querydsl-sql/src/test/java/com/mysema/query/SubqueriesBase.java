package com.mysema.query;

import static com.mysema.query.Constants.employee;
import static com.mysema.query.Constants.employee2;
import static com.mysema.query.Constants.survey;
import static com.mysema.query.Constants.survey2;
import static com.mysema.query.Target.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.mysema.query.sql.SQLSerializer;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.domain.QEmployee;
import com.mysema.query.support.Expressions;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.PathBuilder;
import com.mysema.query.types.query.ListSubQuery;
import com.mysema.testutil.ExcludeIn;

public class SubqueriesBase extends AbstractBaseTest {
    
    @Test
    @SkipForQuoted
    public void SubQueries() throws SQLException {
        // subquery in where block
        expectedQuery = "select e.ID from EMPLOYEE e "
            + "where e.ID = (select max(e.ID) "
            + "from EMPLOYEE e)";
        List<Integer> list = query().from(employee)
        .where(employee.id.eq(sq().from(employee).unique(employee.id.max())))
        .list(employee.id);
        assertFalse(list.isEmpty());
    }

    
    @Test
    public void SubQuery_Alias() {
        query().from(sq().from(employee).list(employee.all()).as(employee2)).list(employee2.all());
    }

    @Test
    @ExcludeIn(SQLITE)
    public void SubQuery_All() {
        query().from(employee).where(employee.id.gtAll(
                sq().from(employee2).list(employee2.id))).count();
    }
    
    @Test
    @ExcludeIn(SQLITE)
    public void SubQuery_Any() {
        query().from(employee).where(employee.id.gtAny(
                sq().from(employee2).list(employee2.id))).count();
    }
    
    @Test
    public void SubQuery_InnerJoin(){
        ListSubQuery<Integer> sq = sq().from(employee2).list(employee2.id);
        QEmployee sqEmp = new QEmployee("sq");
        query().from(employee).innerJoin(sq, sqEmp).on(sqEmp.id.eq(employee.id)).list(employee.id);

    }

    @Test
    public void SubQuery_LeftJoin(){
        ListSubQuery<Integer> sq = sq().from(employee2).list(employee2.id);
        QEmployee sqEmp = new QEmployee("sq");
        query().from(employee).leftJoin(sq, sqEmp).on(sqEmp.id.eq(employee.id)).list(employee.id);

    }
    
    @Test
    @ExcludeIn(SQLITE)
    public void SubQuery_RightJoin(){
        ListSubQuery<Integer> sq = sq().from(employee2).list(employee2.id);
        QEmployee sqEmp = new QEmployee("sq");
        query().from(employee).rightJoin(sq, sqEmp).on(sqEmp.id.eq(employee.id)).list(employee.id);
    }

    @Test
    public void SubQuery_with_Alias(){
        List<Integer> ids1 = query().from(employee).list(employee.id);
        List<Integer> ids2 = query().from(sq().from(employee).list(employee.id), employee).list(employee.id);
        assertEquals(ids1, ids2);
    }

    @Test
    public void SubQuery_with_Alias2(){
        List<Integer> ids1 = query().from(employee).list(employee.id);
        List<Integer> ids2 = query().from(sq().from(employee).list(employee.id).as(employee)).list(employee.id);
        assertEquals(ids1, ids2);
    }

    @Test
    @ExcludeIn({DERBY, H2, HSQLDB, SQLITE}) 
    public void List_In_Query() {
        QEmployee employee2 = new QEmployee("employee2");
        query().from(employee)
            .where(Expressions.list(employee.id, employee.lastname)
                .in(sq().from(employee2).list(employee2.id, employee2.lastname)))
            .list(employee.id);
    }
    
    @Test
    public void SubQuerySerialization(){
        SQLSubQuery query = sq();
        query.from(survey);
        assertEquals("from SURVEY s", query.toString());

        query.from(survey2);
        assertEquals("from SURVEY s, SURVEY s2", query.toString());
    }

    @Test
    public void SubQuerySerialization2(){
        NumberPath<BigDecimal> sal = new NumberPath<BigDecimal>(BigDecimal.class, "sal");
        PathBuilder<Object[]> sq = new PathBuilder<Object[]>(Object[].class, "sq");
        SQLSerializer serializer = new SQLSerializer(SQLTemplates.DEFAULT);

        serializer.handle(
                sq()
                .from(employee)
                .list(employee.salary.add(employee.salary).add(employee.salary).as(sal))
                .as(sq));
        assertEquals(
                "(select (e.SALARY + e.SALARY + e.SALARY) as sal\nfrom EMPLOYEE e) as sq", 
                serializer.toString());
    }
    

}
