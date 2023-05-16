package jpa;

import model.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class HelloJPA03 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        try {
            // 사원 데이터 조회
            // createQuery(질의문, 리턴될객체종류)
            String jpql = "select e from Employee as e";
            // List<Employee> emps = em.createQuery(jpql, Employee.class).getResultList();

            // for (Employee emp : emps)
            //     System.out.println(emp);

            // 사원 데이터 조회 - Query : 이름, 부서번호, 입사일
            // createQuery(질의문)
            jpql = "select fname, deptid, hdate from Employee e";
            // List<Object[]> items = em.createQuery(jpql).getResultList();

            // for (Object[] item : items)
            //     System.out.println(item[0] + "/" + item[1] + "/" + item[2]);

            // 사원 직책 조회 - jodbid가 IT_PROG인 사원
            // 파라메터 바이딩 - : 파라메터명, ?순번
            jpql = "select e from  Employee e where jobid = : jobid";  // 이름 기반
            // jpql = "select e from  Employee e where jobid = : ?1";  // 위치 기반

            TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
            query.setParameter("jobid", "IT_PROG");
            // query.setParameter("1", "IT_PROG");

            // emps = query.getResultList();

            // for (Employee emp : emps)
            //     System.out.println(emp);

            // 사원 평균 연봉 조회 - TypedQuery
            jpql = "select avg(sal) from Employee e";
            // Double avgsal = em.createQuery(jpql, Double.class).getSingleResult();

            // System.out.println(avgsal);

            // 사원 직책수 조회
            // jpql = "select count(jobid) from Employee e";
            jpql = "select count(distinct jobid) from Employee e";
            // Long cntjdb = em.createQuery(jpql, Long.class).getSingleResult();

            // System.out.println(cntjdb);

            // jobid으로 정렬후 3 페이지 조회 : 페이징 (페이징당 출력건수 : 15)
            // setFirstResult(startpos) : 페이징 시작 위치
            // setMaxResult(getdatacnt) : 조회할 데이터 수
            jpql = "select e from Employee e order by jobid";
            // List<Employee> pemps = em.createQuery(jpql, Employee.class)
            //         .setFirstResult(30).setMaxResults(15).getResultList();

            // for (Employee emp : pemps)
            //     System.out.println(emp);


            // 직잭별 평균 연봉 조회
            jpql = "select jobid, avg(sal), count(jobid) from Employee e group by jobid";
            // List<Object[]> items = em.createQuery(jpql).getResultList();

            // for (Object[] item : items)
            //     System.out.println(item[0] + "/" + item[1] + "/" + item[2]);


            // 사원이름, 직책, 부서명 조회 : join
            jpql = "select e.fname, e.jobid, e.deptid, d.dname from Employee e inner join e.department d";
            List<Object[]> items = em.createQuery(jpql).getResultList();

            for (Object[] item : items)
                System.out.println(item[0] + "/" + item[1] + "/" + item[2] + "/" + item[3]);



        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}