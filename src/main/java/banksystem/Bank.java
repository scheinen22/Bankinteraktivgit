package banksystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bank {

    private int blz;
    private String bankname;
    private final Scanner scanner = new Scanner(System.in);
    private final List<Konto> konten;

    public Bank() {
        this.konten = new ArrayList<>();
    }
    public Bank(int blz, String bankname) {
        this.setBlz(blz);
        this.setBankname(bankname);
        this.konten = new ArrayList<>();
    }
    public String getBankname() {
        return bankname;
    }
    public int getBlz() {
        return blz;
    }
    public void setBlz(int blz) {
        this.blz = blz;
    }
    public void setBankname(String bankname) {
        this.bankname = bankname;
    }
    public String bankinformationenAnzeigenString() {
        return "\nBLZ: " + this.getBlz() + "\nBankname: " + this.getBankname();
    }
    public static void transfer(Konto sender, Konto empfaenger, double betrag, int blz, int iban) {
        if (empfaenger == null || empfaenger.getBank() == null || empfaenger.getBank().getBlz() <= 0) {
            System.out.println("Empfänger nicht vorhanden");
            return;
        }
        if (empfaenger.getBank().getBlz() != blz && empfaenger.getIban() != iban) {
            System.out.println("Überweisung fehlgeschlagen.\nIBAN und Bankleitzahl stimmen nicht überein.\n");
            return;
        }
        if (empfaenger.getBank().getBlz() != blz) {
            System.out.println("Überweisung fehlgeschlagen.\nBankleitzahl stimmt nicht überein.\n");
            return;
        }
        if (empfaenger.getIban() != iban) {
            System.out.println("Überweisung fehlgeschlagen.\nIBAN stimmt nicht überein.\n");
            return;
        }
        if (sender.transaktion(betrag, empfaenger, blz, iban)) {
            System.out.println("Überweisung von Konto " + sender.getIban() + " zu Konto " + empfaenger.getIban() + " abgeschlossen.\n");
        }
    }
    public void addKonto(Konto konto) {
        if (konto == null) {
            System.out.println("Kontoliste ist null.");
        } else {
            this.konten.add(konto);
        }
    }
    public Konto findeKonto(int iban) {
        for (Konto konto : konten) {
            if (konto.getIban() == iban) {
                return konto;
            }
        }
        return null;
    }
    public void interaktivesMenu() {
        boolean running = true;
        String gebenKonto = "Geben Sie Ihre Kontonummer ein: ";
        String ungueltigKonto = "Ungültige Kontonummer";
        String ungueltigBetrag = "Ungültiger Betrag";

        while (running) {
            System.out.println("\n--- Bank Menü ---");
            System.out.println("1. Überweisung");
            System.out.println("2. Einzahlung");
            System.out.println("3. Abheben");
            System.out.println("4. Transaktionen anzeigen");
            System.out.println("5. Kontoinformationen anzeigen");
            System.out.println("6. Beenden");
            System.out.println("Wählen Sie eine Option: ");

            int wahl = scanner.nextInt();
            scanner.nextLine();
            switch (wahl) {
                case 1:
                    ueberweisungInter(gebenKonto, ungueltigKonto, ungueltigBetrag);
                    break;
                case 2:
                    einzahlungInter(gebenKonto, ungueltigKonto, ungueltigBetrag);
                    break;
                case 3:
                    abhebenInter(gebenKonto, ungueltigKonto, ungueltigBetrag);
                    break;
                    case 4:
                        transaktionenAnzeigen(gebenKonto, ungueltigKonto);
                        break;
                        case 5:
                            kontoInfos(gebenKonto, ungueltigKonto);
                            break;
                            case 6:
                                running = false;
                                break;
                                default:
                                    break;
            }
        }
    }
    private void ueberweisungInter(String gebenKonto, String ungueltigKonto, String ungueltigBetrag) {
        System.out.println(gebenKonto);
        int ibansender = Integer.parseInt(scanner.nextLine());
        Konto senderkonto = findeKonto(ibansender);
        if (senderkonto == null) {
            System.out.println(ungueltigKonto);
            return;
        }
        System.out.println("Geben Sie die Empfängerkontonummer ein: ");
        int ibanempfaenger = Integer.parseInt(scanner.nextLine());
        Konto empfaenger = findeKonto(ibanempfaenger);
        if (empfaenger == null) {
            System.out.println("Ungültiger Empfänger");
            return;
        }
        System.out.println("Geben Sie die Bankleitzahl des Empfängers ein: ");
        int blzemp = Integer.parseInt(scanner.nextLine());
        if (blzemp <= 0) {
            System.out.println("Ungültige Bankleitzahl");
            return;
        }
        System.out.println("Geben die den gewünschten Betrag an: ");
        double betragemp = Double.parseDouble(scanner.nextLine());
        if (betragemp <= 0) {
            System.out.println(ungueltigBetrag);
            return;
        }
        transfer(senderkonto, empfaenger, betragemp, blzemp, ibanempfaenger);
    }
    private void einzahlungInter(String gebenKonto, String ungueltigKonto, String ungueltigBetrag) {
        System.out.println(gebenKonto);
        int ibaneing = Integer.parseInt(scanner.nextLine());
        Konto iban = findeKonto(ibaneing);
        System.out.println("Bargeld: " + iban.getKunde().getBargeld());
        if (iban == null) {
            System.out.println(ungueltigKonto);
            return;
        }
        System.out.println("Gebe den gewünschten Betrag ein: ");
        double betragemp2 = Double.parseDouble(scanner.nextLine());
        if (betragemp2 <= 0) {
            System.out.println(ungueltigBetrag);
            return;
        }
        iban.einzahlung(betragemp2);
    }
    private void abhebenInter(String gebenKonto, String ungueltigKonto, String ungueltigBetrag) {
        System.out.println(gebenKonto);
        int ibaneing2 = Integer.parseInt(scanner.nextLine());
        Konto iban2  = findeKonto(ibaneing2);
        if (iban2 == null) {
            System.out.println(ungueltigKonto);
            return;
        }
        System.out.println("Gebe den gewünschten Betrag ein: ");
        double betragemp3 = Double.parseDouble(scanner.nextLine());
        if (betragemp3 <= 0) {
            System.out.println(ungueltigBetrag);
            return;
        }
        if (iban2.getKontostand() - betragemp3 < iban2.getDispolimit()) {
            System.out.println("Abhebung fehlgeschlagen: Konto überzogen. Dispolimit erreicht.");
            return;
        }
        iban2.abheben(betragemp3);
        System.out.println("Abhebung erfolgreich: " + betragemp3 + "€ wurde von deinem Konto abgehoben.");
    }
    private void kontoInfos(String gebenKonto, String ungueltigKonto) {
        System.out.println(gebenKonto);
        int ibaneing5 = Integer.parseInt(scanner.nextLine());
        Konto iban5  = findeKonto(ibaneing5);
        if (iban5 == null) {
            System.out.println(ungueltigKonto);
            return;
        }
        System.out.println(iban5.getBank().bankinformationenAnzeigenString() + iban5.kontoinformationenAnzeigenString() + iban5.getKunde().kundeninformationenAnzeigenString());
    }
    private void transaktionenAnzeigen(String gebenKonto, String ungueltigKonto) {
        System.out.println(gebenKonto);
        int ibaneing4 = Integer.parseInt(scanner.nextLine());
        Konto iban3  = findeKonto(ibaneing4);
        if (iban3 == null) {
            System.out.println(ungueltigKonto);
            return;
        }
        iban3.printTransaktionen();
    }
}

