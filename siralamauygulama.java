package siralamaalguygulama;

import java.util.Arrays;

public class siralamauygulama {

    public static int[] kabarcikSirala(int[] dizi) {
        for (int i = 0; i < dizi.length; i++)
            for (int j = 0; j < dizi.length - i - 1; j++)
                if (dizi[j] > dizi[j + 1]) {
                    int gecici = dizi[j];
                    dizi[j] = dizi[j + 1];
                    dizi[j + 1] = gecici;
                }
        yazdir(dizi);
        return dizi;
        
    }

    public static int[] secmeliSirala(int[] dizi) {
        for (int i = 0; i < dizi.length; i++) {
            int enKucuk = i;
            for (int j = i + 1; j < dizi.length; j++)
                if (dizi[j] < dizi[enKucuk]) enKucuk = j;
            int gecici = dizi[i];
            dizi[i] = dizi[enKucuk];
            dizi[enKucuk] = gecici;
        }
        yazdir(dizi);
        return dizi;
      
    }


    public static int[] eklemeliSirala(int[] dizi) {
        for (int i = 1; i < dizi.length; i++) {
            int t = dizi[i], j = i - 1;
            while (j >= 0 && dizi[j] > t) dizi[j + 1] = dizi[j--];
            dizi[j + 1] = t;
        }
        yazdir(dizi);
        return dizi;
    }

  
    public static int[] birlestirmeliSirala(int[] dizi) {
        if (dizi.length < 2) return dizi; 
        
        int orta = dizi.length / 2;
        int[] sol = new int[orta];
        int[] sag = new int[dizi.length - orta];

 
        System.arraycopy(dizi, 0, sol, 0, orta);
        System.arraycopy(dizi, orta, sag, 0, dizi.length - orta);

        birlestirmeliSirala(sol);
        birlestirmeliSirala(sag);

 
        birlestir(dizi, sol, sag);
        
        return dizi;
    }

    private static void birlestir(int[] dizi, int[] sol, int[] sag) {
        int i = 0, j = 0, k = 0;

        while (i < sol.length && j < sag.length) {
            if (sol[i] <= sag[j]) {
                dizi[k++] = sol[i++];
            } else {
                dizi[k++] = sag[j++];
            }
        }


        while (i < sol.length) dizi[k++] = sol[i++];
        while (j < sag.length) dizi[k++] = sag[j++];
    }   
    
    public static void yazdir(int [] dizi) {
    	for(int i=0;i<dizi.length;i++) {
    	System.out.print(dizi[i]+" ");
    	}
    	System.out.println();
    }
    

   
    public static void main(String[] args) {
        int[] dizi = {64, 32, 25, 12, 11,88,13,45,77,32};

        System.out.println("Orijinal Dizi: " + Arrays.toString(dizi));
        long kabarcik1 = System.currentTimeMillis();
        kabarcikSirala(dizi);
        long kabarcik2 = System.currentTimeMillis();
        System.out.println(kabarcik2-kabarcik1 + "sn");

        long secmeli1 = System.currentTimeMillis();
        secmeliSirala(dizi);
        long secmeli2 = System.currentTimeMillis();
        System.out.println(secmeli2-secmeli1 + "sn");

        
        long eklemeli1 = System.currentTimeMillis();
        eklemeliSirala(dizi);
        long eklemeli2 = System.currentTimeMillis();
        System.out.println(eklemeli2-eklemeli1 + "sn");


        long birlestirmeli1 = System.currentTimeMillis();
        birlestirmeliSirala(dizi);
        yazdir(dizi);
        long birlestirmeli2 = System.currentTimeMillis();
        System.out.println(birlestirmeli2-birlestirmeli1 + "sn");
       


    }



	
}
