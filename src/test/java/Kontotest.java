import banksystem.Bank;
import banksystem.Konto;
import banksystem.Kunde;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Kontotest {

    @Test
    public void einzahlungsTest() {
        Konto konto = new Konto(new Bank(12345, "Sparkasse"), 432323, 28923.12, 2000, 2000, new Kunde("Jochen", "Schmidt", "21.12.2000", "Teststraße"));
        konto.einzahlung(1076.88);
        assertEquals(30000, konto.getKontostand());
    }
    @Test
    public void auszahlungTest() {
        Konto konto = new Konto(new Bank(12345, "Sparkasse"), 432323, 28923.12, 2000, 2000, new Kunde("Jochen", "Schmidt", "21.12.2000", "Teststraße"));
        konto.abheben(30000);
        System.out.println(konto.getKontostand());
        assertTrue(konto.getKontostand() > 0);
    }
    @Test
    public void auszahlungsTestPositiv() {
        Konto konto = new Konto(new Bank(12345, "Sparkasse"), 432323, 28923.12, 2000, 2000, new Kunde("Jochen", "Schmidt", "21.12.2000", "Teststraße"));
        konto.abheben(25000.12);
        assertEquals(3923, konto.getKontostand());
    }
    @Test
    public void transaktionsTest() {
        Konto konto = new Konto(new Bank(12345, "Sparkasse"), 432323, 28923.12, 2000, 2000, new Kunde("Jochen", "Schmidt", "21.12.2000", "Teststraße"));
        Konto konto2 = new Konto(new Bank(1234, "Sparkasse"), 43232, 900, 2000, 2000, new Kunde("Jochen", "Schmidt", "21.12.2000", "Teststraße"));
        Bank.transfer(konto, konto2, 2000, 1234, 43232);
        assertEquals(2900, konto2.getKontostand());
    }
    @Test
    public void transaktionsTestNegative() {
        Konto konto = new Konto(new Bank(12345, "Sparkasse"), 432323, 28923.12, 2000, 2000, new Kunde("Jochen", "Schmidt", "21.12.2000", "Teststraße"));
        Konto konto2 = new Konto(new Bank(1234, "Sparkasse"), 43232, 900, 2000, 2000, new Kunde("Jochen", "Schmidt", "21.12.2000", "Teststraße"));
        Bank.transfer(konto, konto2, -2000, 1234, 43232);
        assertEquals(2900, konto2.getKontostand());
    }
}
