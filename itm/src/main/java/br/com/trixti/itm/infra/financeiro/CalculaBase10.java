package br.com.trixti.itm.infra.financeiro;
public class CalculaBase10 {
	public int getMod10(String num){  
		//variáveis de instancia
		int soma = 0;
		int resto = 0;
		int dv = 0;
	    String[] numeros = new String[num.length()+1];
	    int multiplicador = 2;
	    String aux;
	    String aux2;
	    String aux3;
	    for (int i = num.length(); i > 0; i--) {  	    	
	    	//Multiplica da direita pra esquerda, alternando os algarismos 2 e 1
	    	if(multiplicador%2 == 0){
		    	// pega cada numero isoladamente  
	    		numeros[i] = String.valueOf(Integer.valueOf(num.substring(i-1,i))*2);
	    		multiplicador = 1;
	    	}else{
	    		numeros[i] = String.valueOf(Integer.valueOf(num.substring(i-1,i))*1);
	    		multiplicador = 2;
	    	}
	    }  
	    // Realiza a soma dos campos de acordo com a regra
	    for(int i = (numeros.length-1); i > 0; i--){
	    	aux = String.valueOf(Integer.valueOf(numeros[i]));
	    	if(aux.length()>1){
	    		aux2 = aux.substring(0,aux.length()-1);	    		
	    		aux3 = aux.substring(aux.length()-1,aux.length());
	    		numeros[i] = String.valueOf(Integer.valueOf(aux2) + Integer.valueOf(aux3));	    		
	    	}
	    	else{
	    		numeros[i] = aux;    		
	    	}
	    }
	    //Realiza a soma de todos os elementos do array e calcula o digito verificador
	    //na base 10 de acordo com a regra.	    
	    for(int i = numeros.length; i > 0 ; i--){
	    	if(numeros[i-1] != null){
	    		soma += Integer.valueOf(numeros[i-1]);
	    	}
	    }
	    resto = soma%10;
    	dv = 10 - resto;
    	//retorna o digito verificador
	    return dv;
	}
}
