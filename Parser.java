package progYapisiVeAnlami;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Dosya adını girin: ");
        String dosyaAdi = input.nextLine();
        int hata = 0;

        try {
            File file = new File(dosyaAdi);
            Scanner oku = new Scanner(file);
            int satirNumarasi = 1;

            while (oku.hasNextLine()) {
                String satir = oku.nextLine();
                StringBuilder kelime = new StringBuilder();
                String[] kelimeler = new String[3]; // Maksimum üç kelime bekliyoruz: komut ve iki operand
                int kelimeIndex = 0;

                // Karakter karakter okuyarak kelimeleri ayırma
                for (int i = 0; i < satir.length(); i++) {
                    char karakter = satir.charAt(i);

                    // Boşluk veya virgülde kelimeyi tamamla
                    if (karakter == ' ' || karakter == ',') {
                        if (kelime.length() > 0) {
                            kelimeler[kelimeIndex++] = kelime.toString();
                            kelime.setLength(0); // Biriktirilen kelimeyi sıfırla
                        }
                    } else {
                        kelime.append(karakter);
                    }

                    // Eğer üçüncü kelimeye ulaştıysak, daha fazla işlem yapmaya gerek yok
                    if (kelimeIndex == 3) break;
                }

                // Satır sonunda bir kelime kaldıysa ekle
                if (kelime.length() > 0 && kelimeIndex < 3) {
                    kelimeler[kelimeIndex++] = kelime.toString();
                }

                if (kelimeIndex == 0) {
                    System.out.println("Satır " + satirNumarasi + ": Boş satır okunmuştur.");
                    satirNumarasi++;
                    continue; // Boş satır kontrolü
                }

                String komut = kelimeler[0];
                boolean satirGecerli = true;

                // Komut türüne göre geçerlilik kontrolü
                switch (komut) {
                    case "TOP":
                    case "CRP":
                    case "BOL":
                    case "CIK":
                    case "HRK":
                    case "VE":
                    case "VEY":
                        if (kelimeIndex < 3 || !isOperand(kelimeler[1]) || !isOperand(kelimeler[2])) {
                            System.out.println("Satır " + satirNumarasi + ": " + komut + " komutunda iki geçerli operand bekleniyor.");
                            satirGecerli = false;
                        }
                        break;

                    case "D":
                    case "DB":
                    case "DK":
                    case "DKE":
                    case "DBE":
                    case "DED":
                    case "DE":
                        if (kelimeIndex < 2 || !isEtiket(kelimeler[1])) {
                            System.out.println("Satır " + satirNumarasi + ": " + komut + " komutunda geçerli bir etiket bekleniyor.");
                            satirGecerli = false;
                        }
                        break;

                    case "DEG":
                    case "OKU":
                    case "YAZ":
                        if (kelimeIndex < 2 || !isOperand(kelimeler[1])) {
                            System.out.println("Satır " + satirNumarasi + ": " + komut + " komutunda geçerli bir operand bekleniyor.");
                            satirGecerli = false;
                        }
                        break;

                    default:
                        System.out.println("Satır " + satirNumarasi + ": Geçersiz komut - " + komut);
                        satirGecerli = false;
                }

                // Satırın okunduğu bilgisi
                System.out.println("Bu satır okunmuştur: " + satir);

                // Hata kontrolü ve mesajı
                if (!satirGecerli) {
                    System.out.println("Bu satır hatalıdır: " + satir);
                    hata = 1;
                } else {
                    System.out.println("Bu satırda hata yoktur.");
                }

                satirNumarasi++;
            }

            oku.close();

        } catch (FileNotFoundException e) {
            System.out.println("Dosya bulunamadı: " + e.getMessage());
        }

        if (hata == 0) {
            System.out.println("Syntax hatası yok");
        }
    }

    // Operandın geçerli bir register veya sayı olup olmadığını kontrol eder
    private static boolean isOperand(String operand) {
        return operand.equals("AX") || operand.equals("BX") || operand.equals("CX") || operand.equals("DX") || operand.matches("\\d+");
    }

    // Etiketin geçerli bir etiket olup olmadığını kontrol eder
    private static boolean isEtiket(String etiket) {
        return etiket.matches("ETIKET[1-9]|ETIKET10");
    }
}
