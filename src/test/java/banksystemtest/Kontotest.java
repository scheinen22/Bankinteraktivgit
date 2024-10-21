package banksystemtest;

import banksystem.Bank;
import banksystem.Konto;
import banksystem.Kunde;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KontoTest {

    @Test
    void einzahlungsTest() {
        Konto konto = new Konto(new Bank(12345, "Sparkasse"), 432323, 28923.12, -2000, 2000, new Kunde("Jochen", "Schmidt", "21.12.2000", "Teststraße", 1076.88));
        konto.einzahlung(1076.88);
        assertEquals(30000, konto.getKontostand());
    }
    @Test
    void auszahlungTestNegativeValueNoDispolimit() {
        Konto konto = new Konto(new Bank(12345, "Sparkasse"), 432323, 28923.12, -2000, 2000, new Kunde("Jochen", "Schmidt", "21.12.2000", "Teststraße", 0));
        konto.abheben(30000);
        System.out.println(konto.getKontostand());
        assertFalse(konto.getKontostand() > 0);
    }
    @Test
    void auszahlungsTestNormalValue() {
        Konto konto = new Konto(new Bank(12345, "Sparkasse"), 432323, 28923.12, -2000, 2000, new Kunde("Jochen", "Schmidt", "21.12.2000", "Teststraße", 0));
        konto.abheben(25000.12);
        assertEquals(3923, konto.getKontostand());
    }
    @Test
    void transaktionsTestNormalValue() {
        Konto konto = new Konto(new Bank(12345, "Sparkasse"), 432323, 28923.12, -2000, 2000, new Kunde("Jochen", "Schmidt", "21.12.2000", "Teststraße", 0));
        Konto konto2 = new Konto(new Bank(123456, "Sparkasse"), 2131, 900, -2000, 2000, new Kunde("Kai", "Humboldt", "12.10.2000", "Bevingsweg", 0));
        Bank.transfer(konto, konto2, 2000, 123456, 2131);
        assertEquals(2900, konto2.getKontostand());
    }
    @Test
    void transaktionsTestNegativeBetrag() {
        Konto konto = new Konto(new Bank(12345, "Sparkasse"), 432323, 28923.12, -2000, 2000, new Kunde("Jochen", "Schmidt", "21.12.2000", "Teststraße", 0));
        Konto konto2 = new Konto(new Bank(123456, "Sparkasse"), 2131, 900, -2000, 2000, new Kunde("Kai", "Humboldt", "12.10.2000", "Bevingsweg", 0));
        Bank.transfer(konto, konto2, -2000, 123456, 2131);
        Bank.transfer(konto, konto2, -2000, 123456, 2131);
        assertEquals(900, konto2.getKontostand());
    }
}
