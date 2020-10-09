package victor.training.spring.web.spring.catalin;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BizCode {
   private final DataRepo1 dataRepo1;
   private final DataRepo2 dataRepo2;

   public int method() {
      return dataRepo1.method() + dataRepo2.method();
   }
}
