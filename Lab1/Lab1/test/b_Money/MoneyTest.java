package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		assertEquals(1000,EUR10.getAmount(),0);
		assertEquals(0,EUR0.getAmount(),0);
		assertNotEquals(0,SEK200.getAmount(),0);
	}

	@Test
	public void testGetCurrency() {
		assertSame(SEK,SEK0.getCurrency());
		assertSame(EUR,EUR10.getCurrency());
		assertNotSame(EUR,SEK100.getCurrency());
	}

	@Test
	public void testToString() {
		assertEquals("1000 EUR",EUR10.toString());
		assertEquals("20000 SEK",SEK200.toString());
		assertNotEquals("1000 SEK", SEKn100.toString());
	}

	@Test
	public void testGlobalValue() {
		assertEquals(1500,EUR10.universalValue(),0);
		assertEquals(1500,SEK100.universalValue(),0);
		assertNotEquals(1501,EUR10.universalValue(),0);
	}

	@Test
	public void testEqualsMoney() {
		assertTrue(SEK0.equals(EUR0));
		assertFalse(SEK100.equals(EUR20));
	}

	@Test
	public void testAdd() {
		Money EUR30 = EUR10.add(EUR20);
		assertEquals(3000,EUR30.getAmount(),0);
		assertNotEquals(2999,EUR30.getAmount(),0);
	}

	@Test
	public void testSub() {
		Money SUBEUR = EUR20.sub(SEK100);
		assertEquals(1000,SUBEUR.getAmount(),0);
		assertEquals(0,SEK200.sub(EUR20).getAmount(),0);
	}

	@Test
	public void testIsZero() {
		assertTrue(EUR0.isZero());
		assertFalse(EUR10.isZero());
	}

	@Test
	public void testNegate() {
		assertEquals(-1000,EUR10.negate().getAmount(),0);
		assertEquals(10000,SEKn100.negate().getAmount(),0);
	}

	@Test
	public void testCompareTo() {
		assertTrue("Comparing 100 SEK and 20 EUR, 20 EUR is greater so result should be negative",SEK100.compareTo(EUR20) < 0);
		assertEquals("Comparing 200 SEK and 20 EUR, 200 SEK and 20 EUR are the same so result should be 0", 0, SEK200.compareTo(EUR20));
		assertTrue("Comparing 200 SEK and 10 EUR, 200 SEK is greater so result should be positive",SEK200.compareTo(EUR10) > 0);
		assertFalse("Comparing 200 SEK and 10 EUR, 200 SEK is greater so result should be positive, so following expression should be false", SEK200.compareTo(EUR10) < 0);

	}
}
