package rs.raf.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import rs.raf.demo.model.User;
import rs.raf.demo.repositories.UserRepository;

import java.util.*;

@Component
public class JwtUtil  {
    private final String SECRET_KEY = "MY JWT SECRET";
    UserRepository userRepository;
    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    JwtUtil(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public String extractUsername(String token) {


        // TODO: 12/9/2023

        return (String) extractAllClaims(token).get("subject");
      //  return extractAllClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token){
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public String generateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        claims.put("subject",username);
        User user=userRepository.findUserByEmail(username);

        Set set=new HashSet();
        if(user.isCan_create_users())
        set.add("createuser");

        if(user.isCan_read_users()){

        set.add("readuser");

        }

     //


        if(user.isCan_update_users())
        set.add("updateuser");
        if(user.isCan_delete_users())
        set.add("deleteuser");
        if(user.isCan_add_vacuum())
            set.add("createvacuum");
        if(user.isCan_discharge_vacuum())
            set.add("dischargevacuum");
        if(user.isCan_remove_vacuums())
            set.add("removevacuum");
        if(user.isCan_search_vacuum())
            set.add("searchvacuum");
        if(user.isCan_stop_vacuum())
            set.add("stopvacuum");
        if(user.isCan_start_vacuum())
            set.add("startvacuum");



        String s="test";

        System.out.println(set+" set");

        claims.put("permissions",set);



        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    public boolean validateToken(String token, UserDetails user) {
       // System.out.println(!isTokenExpired(token));
      //  System.out.println(user.getUsername());
      //  System.out.println(extractUsername(token));
        return (user.getUsername().equals(extractUsername(token)) && !isTokenExpired(token));
    }
}
