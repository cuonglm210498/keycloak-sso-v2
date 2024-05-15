package com.lecuong.keycloakssov2.filter;

import com.lecuong.keycloakssov2.modal.response.Payload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;

@Data
@Component
@Order(1)
public class TransactionFilter implements Filter {

    private final ObjectMapper objectMapper;

    private final static Logger LOG = LoggerFactory.getLogger(TransactionFilter.class);

    @Autowired
    Payload payloadData;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) req;

        //Get token from header
        String token = httpRequest.getHeader("Authorization");

        //Transform token to array by "."
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        //Convert payload to json from token
        String payload = new String(decoder.decode(chunks[1]));

        //Parse json to object
        Payload payloadObject = objectMapper.readValue(payload, Payload.class);
        payloadData.setName(payloadObject.getName());

        chain.doFilter(req, res);

    }
}
