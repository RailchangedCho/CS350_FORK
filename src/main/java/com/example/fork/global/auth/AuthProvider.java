package com.example.fork.global.auth;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.softdev.folletto.endUserappapiserver.global.data.entity.EndUser;
import com.softdev.folletto.endUserappapiserver.global.data.entity.Token;
import com.softdev.folletto.endUserappapiserver.global.data.repository.EndUserRepository;
import com.softdev.folletto.endUserappapiserver.global.data.repository.TokenRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

@Component
public class AuthProvider {

    private final EndUserRepository endUserRepository;
    private final TokenRepository tokenRepository;

    @Autowired
    public AuthProvider(EndUserRepository endUserRepository,
                        TokenRepository tokenRepository) {
        this.endUserRepository = endUserRepository;
        this.tokenRepository = tokenRepository;
    }

    public Map<String, String> getUserInfoByAccessToken(String JwtTokenString) {
        /* result map */
        Map<String, String> resultMap = new HashMap<>();
        String endUserId;
        String token;
        /* get adminId from the header string */
        try {
            /* slice JWT token */
            String[] chunks = JwtTokenString.split("\\.");
            /* decode the payload part */
            Base64.Decoder decoder = Base64.getUrlDecoder();
            String payload = new String(decoder.decode(chunks[1]));
            /* String to Json object */
            JSONObject jsonObject = new JSONObject(payload);
            /* get preferred_username */
            endUserId = jsonObject.getString("id");
            token = jsonObject.getString("token");
            resultMap.put("id", endUserId);
            resultMap.put("token", token);
        } catch (Exception e) {
            /* token decoding fails. */
            throw new JWTDecodeException("Failed to decode the access token, while getting adminId." + e.getMessage());
        }
        /* check adminId existence */
        try {
            if (endUserRepository.findById(endUserId).isEmpty()) {
                throw new Exception("Unauthorized user");
            }
        } catch (Exception e) {
            /* The adminId does not exist. */
        }
        return resultMap;
    }

    public Integer getUserAuthType(String userId) {

        if(endUserRepository.findById(userId).isEmpty()) {
            return -1;
        }

        EndUser endUser = endUserRepository.findById(userId).get();
        if (endUser.getRole() == Role.ENDUSER) {
            return 1;
        }
        else if (endUser.getRole() == Role.FRANCHISE_ADMIN) {
            return 0;
        }
        else {
            return -1;
        }
    }

    public Boolean checkUserExist(String userId) {
        Optional<EndUser> endUser = endUserRepository.findById(userId);
        return endUser.isPresent();
    }

    public Boolean checkUserExistBySocialId(String socialId) {
        Optional<EndUser> endUser = endUserRepository.findBySocialId(socialId);
        return endUser.isPresent();
    }

    public Boolean isTokenValid(String tokenId) {
        Optional<Token> token = tokenRepository.findById(tokenId);
        return token.get().getIs_valid();
    }

    public void saveToken(String tokenId) {
        Token token = Token.builder().
                id(tokenId)
                .is_valid(true)
                .build();
        tokenRepository.save(token);
    }

    public void expireToken(String tokenId) {
        Token token = tokenRepository.findById(tokenId).get();
        token.setIs_valid(false);
        tokenRepository.save(token);
    }
}
