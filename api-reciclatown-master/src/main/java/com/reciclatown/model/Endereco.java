package com.reciclatown.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public class Endereco {

    private String rua;
    private Integer numero;
    private String bairro;
    private String cidade;
    private String cep;

}
