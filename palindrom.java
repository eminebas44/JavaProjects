package palindroommm;

public class palindrom {
	public class PalindromKontrol {

	    public static boolean palindromMu(char[] dizi, int baslangic, int bitis) {
	       
	        if (baslangic >= bitis) {
	            return true;
	        }
	        
	        if (dizi[baslangic] != dizi[bitis]) {
	            return false;
	        }
	        
	        return palindromMu(dizi, baslangic + 1, bitis - 1);
	    }

	    public static int uzunlukBul(char[] dizi) {
	        int uzunluk = 0;
	        for (char karakter : dizi) {
	            uzunluk++;
	        }
	        return uzunluk;
	    }

	    public static void main(String[] args) {
	        String giris = "nalan";
	        char[] karakterDizisi = giris.toCharArray(); 

	        int uzunluk = uzunlukBul(karakterDizisi);
	        if (palindromMu(karakterDizisi, 0, uzunluk - 1)) {
	            System.out.println(giris + " bir palindromdur.");
	        } else {
	            System.out.println(giris + " bir palindrom değildir.");
	        }
	    }
	}

}
