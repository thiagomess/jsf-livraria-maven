package br.com.caelum.livraria.tx;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Log
@Interceptor
public class TempoDeExecucaoInterceptor implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@AroundInvoke
	public Object log(InvocationContext context) throws Exception  {
		
		long antes = System.currentTimeMillis();
		
		String metodo = context.getMethod().getName();
		String classe = context.getTarget().getClass().getSuperclass().getSimpleName();
		
		Object retorno = context.proceed();
		
		long depois = System.currentTimeMillis();
		
		long resultado = (depois - antes);
		
		System.out.println("Classe: " + classe + " método: " + metodo + " tempo de execução: " + resultado);
		
		return retorno;
	}

}
