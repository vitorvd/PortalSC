package br.com.santacruz.portal.security;

import br.com.santacruz.portal.contexto.ContextManager;
import br.com.santacruz.portal.enums.PerfilType;
import br.com.santacruz.portal.enums.Status;
import br.com.santacruz.portal.fachada.UserService;
import br.com.santacruz.portal.modelo.User;
import br.com.santacruz.portal.modelo.dto.UserDTO;
import br.com.santacruz.portal.modelo.dto.UserQueryDTO;
import br.com.santacruz.portal.modelo.dto.UserQueryResponseDTO;
import br.com.santacruz.portal.modelo.dto.UserRegisterDTO;
import br.com.santacruz.portal.utils.DateConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@PermitAll
@Component
@Path("auth")
public class AuthEndPoint {

    @Autowired
    private ContextManager contextManager;

    @Autowired
    private UserService userService;

    @POST
    public Response auth(@HeaderParam("login") final String login, @HeaderParam("password") final String password) throws Exception {
        User loggedUser = this.processLogin(login, password);

        String token = login + password + LocalDateTime.now();
        this.contextManager.addToken(token, loggedUser);

        return this.createResponseAuth(token, loggedUser);
    }

    @GET
    @Path("logout")
    public Response logout(@HeaderParam("authorization") final String token){
        if(token != null) this.contextManager.removeToken(token);
        return Response.noContent().build();
    }

    protected User processLogin(String login, String password) throws Exception {
        if(login == null || password == null) throw new Exception("As credenciais são inválidas.");

        User user = userService.autenticate(login, password);
        if(user == null) throw new Exception("O usuário informado não foi encontrado na base de dados.");

        return user;
    }

    protected Response createResponseAuth(final String token, final User user) throws NotAuthorizedException {
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", user);

        final Response response = Response.ok(UserDTO.toDTO(user, token), MediaType.APPLICATION_JSON).build();

        return response;
    }

}
