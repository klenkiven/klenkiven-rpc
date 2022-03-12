package xyz.klenkiven.rpc.example;

import java.io.Serializable;
import java.util.Date;

public class Echo implements Serializable {
    public static final long serialVersionUID = 42L;


    private String message;
    private Date handleTime;

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
