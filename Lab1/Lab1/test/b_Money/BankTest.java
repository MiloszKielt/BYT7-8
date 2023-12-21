package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank",SweBank.getName());
		assertNotEquals("SweBank", Nordea.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK,Nordea.getCurrency());
		assertEquals(DKK,DanskeBank.getCurrency());
		assertNotEquals(DKK,SweBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		assertThrows("The method should throw AccountExistsException as Bob already opened the account",AccountExistsException.class, () -> {
			Nordea.openAccount("Bob");
		});
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		assertThrows("Account does not exist so method should throw AccountDoesNotExistException",AccountDoesNotExistException.class,() -> {
			DanskeBank.deposit("Bob", new Money(2000,DKK));
		});
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		fail("Write test case here");
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		fail("Write test case here");
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		fail("Write test case here");
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		fail("Write test case here");
	}
}
