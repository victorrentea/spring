package sample.shop;

import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = "spring.datasource.url=jdbc:hsqldb:mem:shop")
class HsqldbSequenceDiagramTest extends SequenceDiagramTestBase {
}
