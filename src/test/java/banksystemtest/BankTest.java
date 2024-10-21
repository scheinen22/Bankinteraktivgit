package banksystemtest;

import banksystem.Bank;
import banksystem.Konto;
import banksystem.Kunde;
import org.junit.jupiter.api.Test;

import static banksystem.Bank.transfer;
import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    Bank bank = new Bank();
    Konto konto = new Konto(new Bank(12345, "Sparkasse"), 432323, 28923.12, -2000, 2000, new Kunde("Jochen", "Schmidt", "21.12.2000", "Teststraße", 1076.88));
    Konto konto2 = new Konto(new Bank(123456, "Sparkasse"), 2131, 900, -2000, 2000, new Kunde("Kai", "Humboldt", "12.10.2000", "Bevingsweg", 0));
    Konto konto3 = new Konto();
    Konto konto4 = new Konto();

    @Test
    void transaktionKontoNullPointer() {
        assertThrows(NullPointerException.class, () -> transfer(konto3, konto4, 2000, 123, 123));
    }
    @Test
    void transaktionKontoIbanBLZFalsch() {
        transfer(konto)
    }
}
