package br.com.trixti.itm.threads.financeiro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

public class IntegracaoItauThread extends Thread {

	@Override
	public void run() {
		Process proc = null;
		BufferedReader stdInput = null;
		BufferedReader stdError = null;
		try {
			Runtime rt = Runtime.getRuntime();
			String command = "java -cp "+System.getProperty("user.home")+"/itau/Edi7WebCli/bin/Edi7WebCli.jar webclicfg.Edi7WebCli.Main –cITRIXIN –p001 –tP";
			System.out.println(command);
			 proc = rt.exec(command);

			 stdInput = new BufferedReader(new 
			     InputStreamReader(proc.getInputStream()));

			 stdError = new BufferedReader(new 
			     InputStreamReader(proc.getErrorStream()));

			String s = null;
			while ((s = stdInput.readLine()) != null) {
			    System.out.println(s);
			}

			while ((s = stdError.readLine()) != null) {
			    System.out.println(s);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				Field f = proc.getClass().getDeclaredField("pid");
			    f.setAccessible(true);
				System.out.println("Finalizou o processo "+f.getInt(proc));
				stdInput.close();
				stdError.close();
				proc.destroy();
				proc.destroyForcibly();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
