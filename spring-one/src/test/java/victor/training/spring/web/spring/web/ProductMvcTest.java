package victor.training.spring.web.spring.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import javafx.scene.canvas.GraphicsContext;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.controller.dto.ProductDto;
import victor.training.spring.web.controller.dto.ProductSearchCriteria;
import victor.training.spring.web.domain.Product;
import victor.training.spring.web.domain.ProductCategory;
import victor.training.spring.web.domain.Supplier;
import victor.training.spring.web.repo.ProductRepo;
import victor.training.spring.web.repo.SupplierRepo;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "safety.service.url.base=http://localhost:8989")
@AutoConfigureMockMvc
@ActiveProfiles("db-mem")
public class ProductMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @Rule
    public WireMockRule wireMock = new WireMockRule(8989);
    @Autowired
    private SupplierRepo supplierRepo;
    @Autowired
    private ProductRepo productRepo;

    @Test
    @WithMockUser(roles="ADMIN")
    public void testCreate() throws Exception {
        Long supplierId = supplierRepo.save(new Supplier()).getId();

        ProductDto dto = new ProductDto("CoPac", "SAFE", supplierId, ProductCategory.WIFE);
        String createJson = new ObjectMapper().writeValueAsString(dto);
        mockMvc.perform(post("/product/create")
            .content(createJson)
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andExpect(header().string("Custom-Header", "true"));
//    }
//
//    @Test
//    @WithMockUser(roles="ADMIN")
//    public void testSearch() throws Exception {
//        productRepo.save(new Product().setName("CoPac"));

        ProductSearchCriteria criteria = new ProductSearchCriteria();
        criteria.name = "pA";
        String criteriaJson = new ObjectMapper().writeValueAsString(criteria);

        mockMvc.perform(post("/product/search")
            .content(criteriaJson)
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andExpect(header().string("Custom-Header", "true"))
//            .andExpect(jsonPath("$", hasSize(1)));
            .andExpect(jsonPath("$[0].name").value("CoPac"));
    }



    // The MockMvc EMULATES a HTTP call, w/o Tomcat, w/o any HTTP Worker Thread Pool,
    // I'm running the COntroller in the same thread as the test
    // and since the Transaction in Spring is bound to the current thread ->
    // I can make sure the .search repo works in the same Tx as the test one,
    // in which I INSERTed the Product

//    @Test
//    public void testSearc2h() throws Exception {
//        productRepo.save(new Product().setName("Tree"));
//
//        mockMvc.perform(post("/product/search")
//            .content("{}")
//            .contentType(MediaType.APPLICATION_JSON)
//        )
//            .andExpect(status().isOk())
//            .andExpect(header().string("Custom-Header", "true"))
//
//
//            .andExpect(jsonPath("$[0].name").value("Tree"));
//    }
//

}
