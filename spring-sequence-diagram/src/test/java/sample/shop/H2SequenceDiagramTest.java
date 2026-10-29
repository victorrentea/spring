package sample.shop;

import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = "spring.datasource.url=jdbc:h2:mem:shop;DB_CLOSE_DELAY=-1")
class H2SequenceDiagramTest extends SequenceDiagramTestBase {
}
