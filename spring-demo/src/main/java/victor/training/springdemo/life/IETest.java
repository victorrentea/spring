package victor.training.springdemo.life;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IETest {
	@InjectMocks
	private InvoiceExporter invoiceExporter;
	@Mock
	private LabelService labelService;
	@Test
	public void aa() {
		when(labelService.getCountryName("ES"))
				.thenReturn("Capsuni");
		String s = invoiceExporter.exportInvoice();
		assertEquals("Invoice Country: Capsuni", s);
	}
}
