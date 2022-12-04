package tn.isimg.pfe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExistException extends RuntimeException{

    private String mes;

    public ExistException(String mes) {

        this.mes=mes;
    }


    public String getMes() {

        return mes;
    }

    public void setMes(String mes) {

        this.mes = mes;
    }
}