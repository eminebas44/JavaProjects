package bagliLIsteImpl;

import java.util.Scanner;
import java.util.Random;

class Sarki {
    String baslik;
    String sanatci;
    String tur;
    int sure;
	public Sarki sonraki;

    public Sarki(String baslik, String sanatci, String tur, int sure) {
        this.baslik = baslik;
        this.sanatci = sanatci;
        this.tur = tur;
        this.sure = sure;
    }

    public void cal() {
        System.out.println("caliyor: " + baslik + " - " + sanatci + " (" + tur + ", " + sure + " saniye)");
        try {
            Thread.sleep(sure * 1000);
        } catch (InterruptedException e) {
            System.out.println("Hata olustu!");
        }
    }
}

class MP3Oynatici {
    private Sarki bas;

    public void sarkiEkle(String baslik, String sanatci, String tur, int sure) {
        Sarki yeniSarki = new Sarki(baslik, sanatci, tur, sure);
        if (bas == null) {
            bas = yeniSarki;
        } else {
            Sarki mevcut = bas;
            while (mevcut.sonraki != null) {
                mevcut = mevcut.sonraki;
            }
            mevcut.sonraki = yeniSarki;
        }
        System.out.println("sarki eklendi: " + baslik);
    }

    public void sarkiSil(String baslik) {
        if (bas == null) {
            System.out.println("Liste boş.");
            return;
        }
        if (bas.baslik.equals(baslik)) {
            bas = bas.sonraki;
            System.out.println("Şarkı silindi: " + baslik);
            return;
        }
        Sarki mevcut = bas;
        while (mevcut.sonraki != null) {
            if (mevcut.sonraki.baslik.equals(baslik)) {
                mevcut.sonraki = mevcut.sonraki.sonraki;
                System.out.println("sarki silindi: " + baslik);
                return;
            }
            mevcut = mevcut.sonraki;
        }
        System.out.println("sarki bulunamadı: " + baslik);
    }

    public void tumSarkilariGoruntule() {
        if (bas == null) {
            System.out.println("liste bos");
            return;
        }
        Sarki mevcut = bas;
        while (mevcut != null) {
            System.out.println(mevcut.baslik + " - " + mevcut.sanatci + " (" + mevcut.tur + ", " + mevcut.sure + " saniye)");
            mevcut = mevcut.sonraki;
        }
    }

    public void sanatciyaGoreCal(String sanatci) {
        Sarki mevcut = bas;
        while (mevcut != null) {
            if (mevcut.sanatci.equals(sanatci)) {
                mevcut.cal();
            }
            mevcut = mevcut.sonraki;
        }
    }

    public void rastgeleCal() {
        if (bas == null) {
            System.out.println("liste bos");
            return;
        }
        Random rastgele = new Random();
        int sayac = 0;
        Sarki mevcut = bas;
        while (mevcut != null) {
            sayac++;
            mevcut = mevcut.sonraki;
        }
        int rastgeleIndeks = rastgele.nextInt(sayac);
        mevcut = bas;
        for (int i = 0; i < rastgeleIndeks; i++) {
            mevcut = mevcut.sonraki;
        }
        mevcut.cal();
    }
}

public class Node {
    public static void main(String[] args) {
        MP3Oynatici oynatici = new MP3Oynatici();
        Scanner tarayici = new Scanner(System.in);

        while (true) {
            System.out.println("\nMP3 menu:");
            System.out.println("1. Sarki Ekle");
            System.out.println("2. Sarki Sil");
            System.out.println("3. Tum Sarkilari Goruntule");
            System.out.println("4. Sanatciya Gore Cal");
            System.out.println("5. Rastgele Cal");
            System.out.println("6. Cikis");
            System.out.print("Seciminiz: ");

            int secim = tarayici.nextInt();
            tarayici.nextLine(); 

            switch (secim) {
                case 1:
                    System.out.print("sarki adi giriniz: ");
                    String baslik = tarayici.nextLine();
                    System.out.print("sanatci adi giriniz: ");
                    String sanatci = tarayici.nextLine();
                    System.out.print("sarki turu giriniz: ");
                    String tur = tarayici.nextLine();
                    System.out.print("sureyi giriniz (saniye): ");
                    int sure = tarayici.nextInt();
                    oynatici.sarkiEkle(baslik, sanatci, tur, sure);
                    break;
                case 2:
                    System.out.print("silmek istediginiz sarkiyi giriniz: ");
                    String silinecekBaslik = tarayici.nextLine();
                    oynatici.sarkiSil(silinecekBaslik);
                    break;
                case 3:
                    oynatici.tumSarkilariGoruntule();
                    break;
                case 4:
                    System.out.print("sanatci adi: ");
                    String sanatciAdi = tarayici.nextLine();
                    oynatici.sanatciyaGoreCal(sanatciAdi);
                    break;
                case 5:
                    oynatici.rastgeleCal();
                    break;
                case 6:
                    System.out.println("cikis yapiliyorrr... <3");
                    tarayici.close();
                    return;
                default:
                    System.out.println("gecersiz secim!!! tekrar deneyiniz!!!");
            }
        }
    }


}
