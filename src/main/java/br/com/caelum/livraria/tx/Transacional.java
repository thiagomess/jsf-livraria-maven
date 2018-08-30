package br.com.caelum.livraria.tx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.interceptor.InterceptorBinding;

@InterceptorBinding // essa anotação diz que @Transacional está associada a um interceptador
@Retention(RetentionPolicy.RUNTIME) //Significa que a anotação serve para Meotodo e classe
@Target({ElementType.METHOD, ElementType.TYPE}) //significa que roda em tempo de execucao
public @interface Transacional {
}
