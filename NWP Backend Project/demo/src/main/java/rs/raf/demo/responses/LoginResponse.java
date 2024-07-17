package rs.raf.demo.responses;

import lombok.Data;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;

@Data
public class LoginResponse {
    private String jwt;
    private Set<String> permissions;
    public LoginResponse(String jwt, Set<String> permissions) {
        this.jwt = jwt;
        this.permissions=permissions;
    }
}
