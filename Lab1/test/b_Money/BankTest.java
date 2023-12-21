package b_Money;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;

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

		// Initially failed with the returned exception being NullPointerException instead of AccountExistsException
		assertThrows("The method should throw AccountExistsException as Bob already opened the account",AccountExistsException.class, () -> {
			Nordea.openAccount("Bob");
		});

		Nordea.openAccount("Adrian");
		assertThrows("The method should throw AccountExistsException as Adrian already opened the account",AccountExistsException.class, () -> {
			Nordea.openAccount("Adrian");
		});
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		// Initially failed with the returned exception being NullPointerException instead of AccountDoesNotExistException
		assertThrows("Account does not exist so method should throw AccountDoesNotExistException",AccountDoesNotExistException.class,() -> {
			DanskeBank.deposit("Bob", new Money(2000,DKK));
		});
		SweBank.deposit("Bob", new Money(1000,SEK));
		assertEquals("After the deposit Bob's account balance should be equal to 1000 SEK",
				1000, SweBank.getBalance("Bob"),0);
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		assertThrows("Account does not exist so method should throw AccountDoesNotExistException",AccountDoesNotExistException.class,() -> {
			DanskeBank.withdraw("Bob", new Money(3000,DKK));
		});

		SweBank.deposit("Bob", new Money(1000,SEK));
		SweBank.withdraw("Bob", new Money(500,SEK));

		// Initially failed with the actual value being 1500
		assertEquals("After the withdrawal Bob's account balance should be equal to 500 SEK",
				500, SweBank.getBalance("Bob"),0);
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		SweBank.deposit("Bob", new Money(1000,SEK));
		assertEquals("With freshly deposited money bob's account balance should be equal to 1000SEK",
				1000,SweBank.getBalance("Bob"),0);
		assertThrows(AccountDoesNotExistException.class,() -> {
			SweBank.deposit("Ajax",new Money(1000,SEK));
		});
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		SweBank.deposit("Bob", new Money(1000,SEK));
		SweBank.transfer("Bob",Nordea,"Bob",new Money(500, SEK));
		assertEquals("After the transfer Bob's second account should have 500 SEK in the balance",
				500, Nordea.getBalance("Bob"),0);


		SweBank.transfer("Bob", DanskeBank, "Gertrud", new Money(250,SEK));
		assertEquals("After the transfer Gertrud's account should have 185 DKK in the balance",
				185, DanskeBank.getBalance("Gertrud"),0);

		assertThrows("Bob transferring money to a nonexisting account should result in the method throwing an error"
				,AccountDoesNotExistException.class, () -> {
					SweBank.transfer("Bob", DanskeBank, "Marley", new Money(250,SEK));
				});

		assertEquals("After unsuccesful transfer of the money Bob's account balance should not change"
				, 250, SweBank.getBalance("Bob"),0);
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		assertThrows("Adding time payment to non-existing account should result in appropriate exception being thrown",
				AccountDoesNotExistException.class, () -> {
			SweBank.addTimedPayment("Bart","1001",4,5,new Money(20,SEK),Nordea,"Anne");
		});
		SweBank.deposit("Bob", new Money(10000, SEK));
		SweBank.addTimedPayment("Bob", "10001", 1, 1, new Money(10,SEK),Nordea,"Bob");
		SweBank.tick();
		SweBank.tick();
		SweBank.tick();
		SweBank.tick();

		// Initially failed with the actual value of Bob's balance in Nordea Bank being 40
		assertEquals("Bob's account balance in Nordea Bank should be equal to 20 SEK after 4 ticks (The transfer should be done every other tick)"
				, 20, Nordea.getBalance("Bob"),0);


	}
}
