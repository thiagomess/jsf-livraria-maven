package br.com.caelum.livraria.modelo;

import java.util.Random;

public class TestaRandom {
	

	  public static void main(String[] args) {
		  long millis = System.currentTimeMillis();
	        Random gerador = new Random(millis);

	        int resultado = gerador.nextInt();
	        System.out.println(resultado);

	        int resultado2 = gerador.nextInt();
	        System.out.println(resultado2);
	        
	        Random geradorBoolean = new Random(millis);

	        boolean valor = geradorBoolean.nextBoolean();
	        System.out.println(valor);

	        boolean valor2 = geradorBoolean.nextBoolean();
	        System.out.println(valor2);
	        
	        
	    }

	}

