package victor.spring.web;

import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class TradeRepo {
    public List<TradeSearchResult> search(TradeSearchCriteria criteria) {
//        Map<String, Object> params = new HashMap<>();
//        String jpql = "SELECT new victor.spring.web.TradeSearchResult() FROM  x WHERE 1=1 ";
//
//        if (criteria.aaa != null) {
//            jpql += " AND x.isConfidential = :aaa";
//            params.put("aaa", criteria.canSeeConfidential);
//        }
//        javax.persistence.TypedQuery<> query = em.createQuery(jpql, .class);
//        for (String key : params.keySet()) {
//            query.setParameter(key, params.get(key));
//        }
//        return query.getResultList();
        System.out.println("Caut cu canSeeConfidential = " + criteria.canSeeConfidential);
        return Collections.emptyList();
    }
}
