package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.20);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		assertEquals("SEK",SEK.getName());
		assertEquals("DKK",DKK.getName());
		assertNotEquals("SEK",EUR.getName());
	}
	
	@Test
	public void testGetRate() {
		assertEquals(0.20,SEK.getRate(),0.001);
		assertNotEquals(0.15,EUR.getRate(),0.001);
	}
	
	@Test
	public void testSetRate() {
		SEK.setRate(0.15);
		assertNotEquals(0.20,SEK.getRate(),0);
		assertEquals(0.15,SEK.getRate(),0);
	}
	
	@Test
	public void testGlobalValue() {
		int amount = 100;
		assertEquals("Converted value of EUR should be 150 for 100 EUR with rate of 1.5",150,EUR.universalValue(amount),0);
		assertEquals("Converted value of SEK should be 20 for 100 SEK with rate of 0.2",20,SEK.universalValue(amount),0);
	}
	
	@Test
	public void testValueInThisCurrency() {
		int valueEUR = 100;
		assertEquals("The value of 100 EUR converted to SEK should be equal to 30SEK based on the exchange rates"
				,30,SEK.valueInThisCurrency(valueEUR,EUR),0);
	}

}
