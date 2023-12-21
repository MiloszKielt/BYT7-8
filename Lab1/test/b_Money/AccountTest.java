package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() {
		testAccount.addTimedPayment("1",0,0,new Money(1000,SEK),SweBank,"Alice");
		assertTrue(testAccount.timedPaymentExists("1"));
		testAccount.removeTimedPayment("1");
		assertFalse(testAccount.timedPaymentExists("1"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		testAccount.addTimedPayment("1",0,0,new Money(1000,SEK),SweBank,"Alice");
		testAccount.tick();
		testAccount.tick();
		assertEquals("Alice's account balance should increase by 1000 SEK with each tick"
				, 1002000,SweBank.getBalance("Alice"),0);
	}

	@Test
	public void testAddWithdraw() {
		testAccount.deposit(new Money(10000000, SEK));
		assertEquals("After depositing account balance should increase by 10000000"
				, 20000000, testAccount.getBalance().getAmount(), 0);

		testAccount.withdraw(new Money(15000000, SEK));
		assertEquals("After withdrawal account balance should be equal to 5000000"
				, 5000000, testAccount.getBalance().getAmount(), 0);

	}
	
	@Test
	public void testGetBalance() {
		assertEquals("Initial call to getBalance method on testAccount should result in getting 10000000 SEK as " +
				"result", 10000000, testAccount.getBalance().getAmount(), 0);
		assertEquals("The currency in the balance should be SEK", "SEK"
				, testAccount.getBalance().getCurrency().getName());
	}
}
