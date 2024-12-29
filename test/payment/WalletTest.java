package payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import payment.exceptions.InvalidAmountException;
import payment.exceptions.NotEnoughWalletException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {
    private Wallet wallet;

    @BeforeEach
    void setUp() {
        wallet = new Wallet(BigDecimal.valueOf(100));
    }

    @Test
    void testAddAmount() {
        wallet.addAmount(BigDecimal.valueOf(50));
        assertEquals(BigDecimal.valueOf(150), wallet.getAmount(), "Adding 50 should result in 150");
    }

    @Test
    void testSubtractAmount() {
        wallet.subtractAmount(BigDecimal.valueOf(30));
        assertEquals(BigDecimal.valueOf(70), wallet.getAmount(), "Subtracting 30 should result in 70");
    }

    @Test
    void testSubtractAmount_InsufficientFunds() {
        BigDecimal toSubtract = BigDecimal.valueOf(150);
        Exception exception = assertThrows(NotEnoughWalletException.class, () -> wallet.subtractAmount(toSubtract));
        assertEquals("There is not enough money in the wallet", exception.getMessage());
    }

    @Test
    void testAddAmount_NegativeValue() {
        InvalidAmountException exception = assertThrows(
                InvalidAmountException.class, () -> {
                    wallet.addAmount(BigDecimal.valueOf(-10));
                }
        );
        assertEquals("Amount to add cannot be negative", exception.getMessage());
    }

    @Test
    void testSubtractZero() {
        wallet.subtractAmount(BigDecimal.ZERO);
        assertEquals(BigDecimal.valueOf(100), wallet.getAmount());
    }

    @Test
    void testSubtractNegativeValue() {
        InvalidAmountException exception = assertThrows(
                InvalidAmountException.class, () -> {
                    wallet.subtractAmount(BigDecimal.valueOf(-10));
                }
        );
        assertEquals("Amount to subtract cannot be negative", exception.getMessage());
    }
}
