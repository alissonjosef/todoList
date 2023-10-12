package br.com.alisson.todolist.task.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.alisson.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*  */
@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                String requestURI = request.getRequestURI();

                if (requestURI.startsWith("/task")) {

                    var authorization = request.getHeader("Authorization");
                    System.out.println(authorization);
                    var authEncoded = authorization.substring("Basic".length()).trim();
            
                    byte[] authDecode = Base64.getDecoder().decode(authEncoded);
            
                    var authString = new String(authDecode);
            
                    String[] credentials = authString.split(":");
                    String username = credentials[0];
                    String password = credentials[1];
                    System.out.println(username);
                    System.out.println(password);
            
                    var user =  this.userRepository.findByUsername(username);
                    if (user == null) {
                        response.sendError(401);
                    }else {
            
                       var passwordVeryfy =  BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
            
                      if( passwordVeryfy.verified) {
                          
                          filterChain.doFilter(request, response);
                      }else {
                        response.sendError(401);
                      }
            
                    }
                }else {
                     filterChain.doFilter(request, response);
                }



    }

}