package victor.training.spring.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import victor.training.spring.web.entity.ContractType;
import victor.training.spring.web.entity.Teacher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

//@Repository // not needed
public interface TeacherRepo extends JpaRepository<Teacher, Long> {
   List<Teacher> findByContractType(ContractType contractType);
}

@Repository
class OldSchoolSQLBasedOrWhateerRepo {
   public void method() {

   }
}


class Horror {
   public static void main(String[] args) {
      // dynamic
      InvocationHandler h = new InvocationHandler() {
         @Override
         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return null;
         }
      };
      TeacherRepo repo = (TeacherRepo) Proxy.newProxyInstance(TeacherRepo.class.getClassLoader(),
          new Class<?>[] {TeacherRepo.class},
          h);

//      repo.findById(1L)
   }
}