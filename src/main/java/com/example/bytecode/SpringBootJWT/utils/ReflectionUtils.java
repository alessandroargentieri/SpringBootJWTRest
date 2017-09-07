package com.example.bytecode.SpringBootJWT.utils;

import java.lang.reflect.*;

/**
 * Classe con metodi statici per fare il get e set sui parametri privati di una classe istanziata.
 * @date  7/9.2017
 * @Author Alessandro Argentieri
 */
public class ReflectionUtils {

    /**
     *
     * @param instance              istanza della classe su cui implementare la reflection
     * @param fieldName             stringa che riporta il nome del campo privato da leggere o settare
     * @return privateValue         valore del campo privato
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Object getPrivateField(Object instance, String fieldName) throws NoSuchFieldException, IllegalAccessException{
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        Object privateValue = field.get(instance);
        return privateValue;
    }

    /**
     *
     * @param instance              istanza della classe su cui implementare la reflection
     * @param fieldName             stringa che riporta il nome del campo privato da leggere o settare
     * @param value                 valore del campo privato da settare
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void setPrivateField(Object instance, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException{
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance, value);
    }

}

