
package uf1_seguridad_actividad2;

 
import java.io.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.util.Scanner;
public class UF1_Seguridad_Actividad2 {
   

    public static Scanner s = new Scanner(System.in);
    public static void main(String[] args) {
        
        int opcion=0;
        String fichero;
        String fichero2;
        System.out.println("Bienvenido al programa de cifrado y descifrado DES.");
        while (opcion!=3){
        System.out.println("¿Qué desea hacer?\n"
                + "1. Cifrar\n"
                + "2. Descrifrar\n"
                + "3. Salir");
        opcion = Integer.parseInt(s.nextLine());
        
        switch(opcion){
            
            case 1: System.out.println("Introduzca el nombre del fichero a cifrar:");
            fichero = s.nextLine();
            System.out.println("Introduzca el nombre del fichero cifrado:");
            fichero2 = s.nextLine();
            try {
			String clave = "salmorejo16";
			FileInputStream original = new FileInputStream(fichero);
			FileOutputStream cifradoOut = new FileOutputStream(fichero2);
			Cifrar(clave, original, cifradoOut);
		} catch (Throwable e) {
			e.printStackTrace();
		}          
                    break;
            case 2: System.out.println("Introduzca el fichero a descifrar:");
            fichero = s.nextLine();
            System.out.println("Introduzca el nombre del fichero descifrado:");
            fichero2 = s.nextLine();
            try {
			String clave = "salmorejo161";
			FileInputStream cifradoIn = new FileInputStream(fichero);
			FileOutputStream descifrado = new FileOutputStream(fichero2);
			Descifrar(clave, cifradoIn, descifrado);
		} catch (Throwable e) {
			e.printStackTrace();
		}
                    break;
            case 3: System.out.println("¡Hasta la próxima!");
                    break;
            default: System.out.println("Esta no está disponible. Indique 1 para cifrar o 2 para descrifrar.");        
        }
    }
        
    }
    
    public static void Cifrar(String key, InputStream is, OutputStream os) throws Throwable {
		Clave(key, Cipher.ENCRYPT_MODE, is, os);
	}

	public static void Descifrar(String key, InputStream is, OutputStream os) throws Throwable {
		Clave(key, Cipher.DECRYPT_MODE, is, os);
	}

	public static void Clave(String clave, int modo, InputStream is, OutputStream os) throws Throwable {

		DESKeySpec dks = new DESKeySpec(clave.getBytes());
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey desKey = skf.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES"); // DES/ECB/PKCS5Padding for SunJCE

		if (modo == Cipher.ENCRYPT_MODE) {
			cipher.init(Cipher.ENCRYPT_MODE, desKey);
			CipherInputStream cis = new CipherInputStream(is, cipher);
			doCopy(cis, os);
		} else if (modo == Cipher.DECRYPT_MODE) {
			cipher.init(Cipher.DECRYPT_MODE, desKey);
			CipherOutputStream cos = new CipherOutputStream(os, cipher);
			doCopy(is, cos);
		}
	}

	public static void doCopy(InputStream is, OutputStream os) throws IOException {
		byte[] bytes = new byte[64];
		int numBytes;
		while ((numBytes = is.read(bytes)) != -1) {
			os.write(bytes, 0, numBytes);
		}
		os.flush();
		os.close();
		is.close();
	}
    
  
}
