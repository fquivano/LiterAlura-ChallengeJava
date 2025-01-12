package com.alura.literatura.enums;

public enum Genero {

    ACCION ("Action"),
    ROMANCE ("Romance"),
    CRIMEN ("Crime"),
    COMEDIA ("Comedy"),
    DRAMA ("Drama"),
    AVENTURA ("Adventure"),
    FICCION ("Fiction"),
    MYSTERY("Mystery"),
    DESCONOCIDO("Desconocido");

    private String genero;

    Genero(String genero) {
        this.genero = genero;
    }

    public static Genero fromString(String text){
        for (Genero generoEnum: Genero.values()){
            if (generoEnum.genero.equalsIgnoreCase(text)){
                return generoEnum;
            }
        }
        return Genero.DESCONOCIDO;
    }
}
