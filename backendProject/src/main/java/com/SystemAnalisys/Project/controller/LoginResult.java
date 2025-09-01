package com.SystemAnalisys.Project.controller;

import com.SystemAnalisys.Project.entity.Usuario;

public class LoginResult {

    private boolean success;
    private String message;
    private Usuario usuario;
    private String code;
    private boolean requirePasswordChange;
    private String passwordChangeReason;
    private String sessionId;

     public LoginResult(boolean success, String message, Usuario usuario, String code) {
        this.success = success;
        this.message = message;
        this.usuario = usuario;
        this.code = code;
        this.requirePasswordChange = false;
    }

    public LoginResult(boolean success, String message, Usuario usuario, String code,
                       boolean requirePasswordChange, String passwordChangeReason, String sessionId) {
        this.success = success;
        this.message = message;
        this.usuario = usuario;
        this.code = code;
        this.requirePasswordChange = requirePasswordChange;
        this.passwordChangeReason = passwordChangeReason;
        this.sessionId = sessionId;
    }

    // Getters y Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public boolean isRequirePasswordChange() { return requirePasswordChange; }
    public void setRequirePasswordChange(boolean requirePasswordChange) { this.requirePasswordChange = requirePasswordChange; }

    public String getPasswordChangeReason() { return passwordChangeReason; }
    public void setPasswordChangeReason(String passwordChangeReason) { this.passwordChangeReason = passwordChangeReason; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }


}
