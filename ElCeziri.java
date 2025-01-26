package com.emine;

import java.io.*;
import java.util.*;

public class ElCezeriIslemci {
    private static final int MAX_8BIT = 255; 
    private static final int MIN_8BIT = 0; 

    private static int AX = 0;
    private static int BX = 0;
    private static int CX = 0;
    private static int DX = 0;

    public static void main(String[] args) {
        Scanner tarayici = new Scanner(System.in);

        System.out.print("Dosya adi giriniz: ");
        String dosyaAdi = tarayici.nextLine();

        try {
            List<String> komutlar = dosyadanKomutOku(dosyaAdi);

            for (int i = 0; i < komutlar.size(); i++) {
                komutuIsle(komutlar.get(i));
            }
        } catch (Exception hata) {
            System.out.println("Hata: " + hata.getMessage());
        }
    }

    private static List<String> dosyadanKomutOku(String dosyaAdi) throws Exception {
        List<String> komutlar = new ArrayList<>();
        BufferedReader okuyucu = new BufferedReader(new FileReader(dosyaAdi));

        String satir;
        while ((satir = okuyucu.readLine()) != null) {
            komutlar.add(satir.trim());
        }

        okuyucu.close();
        return komutlar;
    }

    private static void komutuIsle(String komut) {
        String[] parcalar = parcala(komut, " ");
        if (parcalar.length != 2) {
            System.out.println("Gecersiz Komut: " + komut);
            return;
        }

        String islem = parcalar[0];
        String[] operandlar = parcala(parcalar[1], ",");
        if (operandlar.length != 2) {
            System.out.println("Gecersiz operandlar: " + komut);
            return;
        }

        if (esitMi(islem, "HRK")) {
            degerAta(operandlar[0], degerAl(operandlar[1]));
        } else if (esitMi(islem, "TOP")) {
            toplamaYap(operandlar[0], operandlar[0], operandlar[1]);
        } else if (esitMi(islem, "CRP")) {
        	carpmaYap(operandlar[0], operandlar[0], operandlar[1]);
        	} else if (esitMi(islem, "CIK")) {
            cikarmaYap(operandlar[0], operandlar[0], operandlar[1]);
            
        } else if (esitMi(islem, "BOL")) {
            bolmeYap(operandlar[0], operandlar[0], operandlar[1]);
        } else {
            System.out.println("Geçersiz işlem komutu: " + islem);
        }

        kayitlariYazdir();
    }

    private static String[] parcala(String metin, String ayrac) {
        List<String> parcalar = new ArrayList<>();
        int baslangic = 0;
        for (int i = 0; i < metin.length(); i++) {
            if (metin.substring(i, i + 1).equals(ayrac)) {
                parcalar.add(metin.substring(baslangic, i));
                baslangic = i + 1;
            }
        }
        parcalar.add(metin.substring(baslangic));
        return parcalar.toArray(new String[0]);
    }

    private static boolean esitMi(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private static int degerAl(String operand) {
        if (esitMi(operand, "AX")) {
            return AX;
        } else if (esitMi(operand, "BX")) {
            return BX;
        } else if (esitMi(operand, "CX")) {
            return CX;
        } else if (esitMi(operand, "DX")) {
            return DX;
        } else {
            int deger = metniSayiyaCevir(operand);
            if (deger < MIN_8BIT || deger > MAX_8BIT) {
                System.out.println("Uyari: 8-bit sinirlarinin disinda bir deger");
            }
            return deger; 
        }
    }

    private static int metniSayiyaCevir(String metin) {
        int sayi = 0;
        for (int i = 0; i < metin.length(); i++) {
            sayi = sayi * 10 + (metin.charAt(i) - '0');
        }
        return sayi;
    }

    private static void degerAta(String kayit, int deger) {
        if (deger < MIN_8BIT || deger > MAX_8BIT) {
            System.out.println("Uyarı: 8-bit siniri asildi");
        }
        else {
        	if (esitMi(kayit, "AX")) {
        		AX = deger;
        	} else if (esitMi(kayit, "BX")) {
        		BX = deger;
        	} else if (esitMi(kayit, "CX")) {
        		CX = deger;
        	} else if (esitMi(kayit, "DX")) {
        		DX = deger;
        	}
        	
        }
    }

    private static void toplamaYap(String hedef, String op1, String op2) {
        int sonuc = degerAl(op1) + degerAl(op2);
        System.out.println("TOP " + hedef + "," + op2 + "  Toplama sonucu: " + degerAl(op1) + " + " + degerAl(op2) + " = " + sonuc);
        degerAta(hedef, sonuc);
    }

    private static void cikarmaYap(String hedef, String op1, String op2) {
        int sonuc = degerAl(op1) - degerAl(op2);
        if (sonuc < 0) {
            System.out.println("Uyarı: Sonuç negatif: " + sonuc);
        }else {
        	System.out.println("CIK " + hedef + "," + op2 + "  Cıkarma sonucu: " + degerAl(op1) + " - " + degerAl(op2) + " = " + sonuc);
        	degerAta(hedef, sonuc);
        }
    }

    private static void carpmaYap(String hedef, String op1, String op2) {
        int sonuc = degerAl(op1) * degerAl(op2);
//        for (int i = 0; i < degerAl(op2); i++) {
//            sonuc += degerAl(op1);
//        }
        if(sonuc<255) {
        	System.out.println("CRP " + hedef + "," + op2 + " Carpma sonucu: " + degerAl(op1) + " * " + degerAl(op2) + " = " + sonuc);
        	degerAta(hedef, sonuc);
        }
    }

    private static void bolmeYap(String hedef, String op1, String op2) {
        if (degerAl(op2) == 0) {
            System.out.println("Hata: Sifira bölme hatası!");
            return;
        }
        
        int sonuc = degerAl(op1) / degerAl(op2);
        if(sonuc<255) {
        	System.out.println("BOL " + hedef + "," + op2 + " işlendi. Bölme sonucu: " + degerAl(op1) + " ÷ " + degerAl(op2) + " = " + sonuc);
        	degerAta(hedef, sonuc);
        }
    }

    private static void kayitlariYazdir() {
        System.out.println("AX: " + AX + ", BX: " + BX + ", CX: " + CX + ", DX: " + DX);
    }
}
