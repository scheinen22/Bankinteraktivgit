package banksystemtest;

import banksystem.Bank;
import banksystem.Konto;
import banksystem.Kunde;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KontoTest {

    Konto konto = new Konto(new Bank(12345, "Sparkasse"), 432323, 28923.12, -2000, 2000, new Kunde("Jochen", "Schmidt", "21.12.2000", "Teststraße", 1076.88));
    Konto konto2 = new Konto(new Bank(123456, "Sparkasse"), 2131, 900, -2000, 2000, new Kunde("Kai", "Humboldt", "12.10.2000", "Bevingsweg", 0));

    @Test
    void einzahlungsTest() {
        konto.einzahlung(1076.88);
        assertEquals(30000, konto.getKontostand());
    }
    @Test
    void einzahlungsTestBargeldNichtVorhanden() {
        konto2.einzahlung(1077);
        assertEquals(900, konto2.getKontostand());
    }
    @Test
    void einzahlungsTestBargeldNegativ() {
        konto2.einzahlung(-2000);
        assertEquals(900, konto2.getKontostand());
    }
    @Test
    void einzahlungsTestBargeldNegativNachEinzahlung() {
        konto.einzahlung(2000);
        assertEquals(28923.12, konto.getKontostand());
    }
    @Test
    void auszahlungTestNegativeValueNoDispolimit() {
        konto.abheben(30000);
        assertFalse(konto.getKontostand() > 0);
    }
    @Test
    void auszahlungTestNegativeValue() {
        konto.abheben(-2000);
        assertTrue(konto.getKontostand() > 0);
    }
    @Test
    void auszahlungsTestDispolimitReached() {
        konto2.abheben(3000);
        assertTrue(konto2.getKontostand() > 0);
    }
    @Test
    void auszahlungsTestNormalValue() {
        konto.abheben(25000.12);
        assertEquals(3923, konto.getKontostand());
    }
    @Test
    void transaktionsTestNormalValue() throws Exception {
        Bank.transfer(konto, konto2, 2000, 123456, 2131);
        assertEquals(2900, konto2.getKontostand());
    }
    @Test
    void transaktionsTestNegativeBetrag() throws Exception {
        Bank.transfer(konto, konto2, -2000, 123456, 2131);
        Bank.transfer(konto, konto2, -2000, 123456, 2131);
        assertEquals(900, konto2.getKontostand());
    }
    @Test
    void transaktionsTestWrongBLZ() throws Exception {
        Bank.transfer(konto, konto2, 2000, 12345, 2131);
        assertEquals(900, konto2.getKontostand());
    }
    @Test
    void transaktionsTestWrongIban() throws Exception {
        Bank.transfer(konto, konto2, 2000, 123456, 21312);
        assertEquals(900, konto2.getKontostand());
    }
    @Test
    void transaktionsTestLimitExtended() throws Exception {
        Bank.transfer(konto, konto2, 10000, 123456, 2131);
        assertEquals(900, konto2.getKontostand());
    }
    @Test
    void transaktionsTestDispolimitExtended() throws Exception {
        Konto konto3 = new Konto(new Bank(123456, "Sparkasse"), 2131, 900, -2000, 20000, new Kunde("Kai", "Humboldt", "12.10.2000", "Bevingsweg", 0));
        Bank.transfer(konto3, konto, 20000, 12345, 432323);
        assertEquals(900, konto3.getKontostand());
    }
}
