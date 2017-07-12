package br.com.trixti.itm.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.xml.ws.BindingType;

@BindingType
@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.TYPE, ElementType.FIELD, ElementType.METHOD,
        ElementType.PARAMETER })
public @interface PropertiesResource {

    @Nonbinding
    public String name();

    @Nonbinding
    public String loader();

}