package tn.isimg.pfe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private String mes;

    public ResourceNotFoundException(String mes) {

        this.mes=mes;
    }


    public String getMes() {

        return mes;
    }

    public void setMes(String mes) {

        this.mes = mes;
    }
}
