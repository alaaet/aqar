package com.arademia.aqar.config.filters;

import com.arademia.aqar.config.service.MyUserDetailsService;
import com.arademia.aqar.config.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class JwtRequestFilter  extends OncePerRequestFilter {
    private static final Logger log = Logger.getLogger(JwtRequestFilter.class.getName());

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = req.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt).toString();
        }
        try {
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt,userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(req,res);
    }catch (SignatureException ex) {
        log.log(Level.SEVERE, "JWT SIGNING INVALID");
    }catch (MalformedJwtException ex) {
        log.log(Level.SEVERE, "JWT STRUCTURE INVALID");
    }catch (ExpiredJwtException ex) {
        log.log(Level.SEVERE, "JWT EXPIRED");
        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString("JWT Expired");
        res.setContentType("application/json");
        res.getWriter().write(body);
        res.getWriter().flush();
        res.getWriter().close();
    }catch (UnsupportedJwtException ex) {
        log.log(Level.SEVERE, "JWT UNSUPPORTED");
    }catch (IllegalArgumentException ex) {
        log.log(Level.SEVERE, "ILLEGAL ARGUMENT JWT ENVIADO");
    }
    }
}
