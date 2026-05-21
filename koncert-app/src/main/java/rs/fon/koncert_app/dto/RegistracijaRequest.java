package rs.fon.koncert_app.dto;

import lombok.Data;

@Data
public class RegistracijaRequest {
    private String ime;
    private String prezime;
    private String email;
    private String password;
}