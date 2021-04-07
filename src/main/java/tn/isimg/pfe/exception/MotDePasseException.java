package tn.isimg.pfe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MotDePasseException extends RuntimeException{

    private String mes;

    public MotDePasseException(String mes) {

        this.mes=mes;
    }


    public String getMes() {

        return mes;
    }

    public void setMes(String mes) {

        this.mes = mes;
    }
}
