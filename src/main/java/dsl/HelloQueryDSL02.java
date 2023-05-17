package dsl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import model.Department;
import model.Employee;
import model.QDepartment;
import model.QEmployee;
import org.apache.commons.lang.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

public class HelloQueryDSL02 {
    public static void main(String[] args) {
        // jpa 객체 초기화 : emf -> em -> etx
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        try {
            // 쿼리 객체 준비
            QEmployee qemp = QEmployee.employee;
            QDepartment qdept = QDepartment.department;
            JPAQueryFactory query = new JPAQueryFactory(em);

            // 조회 - 전체 사원
            /* List<Employee> emps = query.selectFrom(qemp).fetch();

            for (Employee e : emps)
                System.out.println(e)*/;

            // 조회 - 일부 사원, 페이징
            /* List<Employee> emps = query.selectFrom(qemp).offset(30).limit(15).fetch();

            for (Employee e : emps)
                System.out.println(e);*/

            // 사원 데이터 조회 - Query : 이름, 부서번호, 입사일
            /* List<Tuple> itmes = query.select(
                    qemp.fname, qemp.deptid, qemp.hdate).from(qemp).fetch();

            for (Tuple item : itmes)
                System.out.println(item);*/

            // 정렬 : Orderby, 부서번호 기준
            /* query = new JPAQueryFactory(em);
            List<Employee> emps = query.selectFrom(qemp).orderBy(qemp.deptid.desc()).fetch();

            System.out.println(emps);*/

            // 조건검색 : where - 직책인 IT_PROG인 사원 조회
            /* query = new JPAQueryFactory(em);
            List<Employee> emps = query.selectFrom(qemp).where(qemp.jobid.eq("IT_PROG")).fetch();

            System.out.println(emps);*/

            // 조건검색 : 연봉이 20000 이상인 사원 조회
            /* query = new JPAQueryFactory(em);
            List<Employee> emps = query.selectFrom(qemp).where(qemp.sal.goe(20000)).fetch();

            System.out.println(emps);*/

            // 직책 수 조회 1
            /* query = new JPAQueryFactory(em);
            List<Long> cnt = query.select(qemp.jobid.count()).from(qemp).fetch();

            System.out.println(cnt);*/

            // 직책 수 조회 2
            /* Long cnt2 = query.select( qemp.jobid).from(qemp).fetchCount();

            System.out.println(cnt2);*/

            // 직책 수 조회 3
            /* cnt = query.select( qemp.jobid.countDistinct()).from(qemp).fetch();

            System.out.println(cnt);*/

            // 직책 수 조회 4
            /* cnt2 = query.select( qemp.jobid ).distinct().from(qemp).fetchCount();

            System.out.println(cnt2);*/

            // 필터링 : 직책별, 최대, 최소, 평균 연봉, 직책수 조회
           /* query = new JPAQueryFactory(em);
            List<Tuple> grouping = query.select(qemp.jobid, qemp.sal.max(), qemp.sal.min(), qemp.sal.avg(), qemp.jobid.count())
                    .from(qemp).groupBy(qemp.jobid).fetch();

            System.out.println(grouping);*/

            // 필터링 : 직책별, 최대, 최소, 평균 연봉, 직책수 정렬 조회
            /*StringPath jbcnt = Expressions.stringPath("jbcnt");  // 별칭정의

            grouping = query.select(qemp.jobid, qemp.sal.max(), qemp.sal.min(), qemp.sal.avg(), qemp.jobid.count().as("jbcnt"))
                    .from(qemp).groupBy(qemp.jobid).orderBy( jbcnt.desc() ).fetch();

            System.out.println(grouping);*/

            // 서브쿼리1 : 평균연봉보다 작게 받는 사원들 조회
            /* query = new JPAQueryFactory(em);*/

            // 서브쿼리 - 평균연봉은?
            /* JPQLQuery<Double> subqru = JPAExpressions.select(qemp.sal.avg()).from(qemp);*/

            // 주쿼리 - 사원조회
            /* List<Employee> emps = query.selectFrom(qemp).where(qemp.sal.lt(subqru) ).fetch();

            System.out.println(emps);*/

            // 서브쿼리2 : IT 부서에 근무중인 사원들의 이름, 직책, 급여 조회
            query = new JPAQueryFactory(em);

            List<Tuple> items = query.select(
                    qemp.fname, qemp.jobid, qdept.dname).from(qemp).join(qemp.department, qdept).where(qemp.deptid.eq( 60L )).fetch();

            System.out.println(items);

            // 서브쿼리
            JPQLQuery<Long> subqry = JPAExpressions.select(qdept.deptid).from(qdept).where(qdept.dname.eq("IT"));

            // 주쿼리
            /*List<Tuple> items = query.select(qemp.fname, qemp.jobid, qemp.sal).from(qemp).where(qemp.deptid.eq(subqry)).fetch();

            System.out.println(items);*/

            // 제공된 이름, 직책, 연봉으로 사원 조회 : 동적 쿼리
            // 직책이 IT_PROG 인 사원 조회
            // 연봉이 10000이상인 사원 조회
            // 직책이 IT_PROG이고 연봉이 6000 이상인 사원 조회
            String fname = "Ste";
            String jobid = null;
            Integer sal = null;

            query = new JPAQueryFactory(em);
            List<Employee> emps = query.selectFrom(qemp).where(
                    (fname != null) ? qemp.fname.contains(fname) : null,
                    StringUtils.isNotEmpty(jobid) ? qemp.jobid.eq(jobid) : null,
                    (sal != null) ? qemp.sal.eq(sal) : null
            ).fetch();

            System.out.println(emps);


            // 필터링


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
