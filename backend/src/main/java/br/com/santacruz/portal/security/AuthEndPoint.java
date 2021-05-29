package br.com.santacruz.portal.security;

import br.com.santacruz.portal.contexto.ContextManager;
import br.com.santacruz.portal.fachada.UserService;
import br.com.santacruz.portal.modelo.User;
import br.com.santacruz.portal.modelo.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@PermitAll
@Component
@Path("auth")
public class AuthEndPoint {

    @Autowired
    private ContextManager contextManager;

    @Autowired
    private UserService userService;

    @GET
    @Path("register")
    public Response register() {
        User user = User.builder()
                .login("vitor")
                .passwordHash(BCrypt.hashpw("teste", BCrypt.gensalt(10)))
                .lastAccess(new Date()).build();
        userService.save(user);
        return Response.ok().build();
    }

    @POST
    public Response auth(@HeaderParam("login") final String login, @HeaderParam("password") final String password) throws Exception {
        User loggedUser = this.processLogin(login, password);

        String token = login + password + LocalDateTime.now();
        this.registerToken(token, loggedUser);

        return this.createResponseAuth(token, loggedUser);
    }

    @GET
    @Path("logout")
    public Response logout(@HeaderParam("authorization") final String token){
        if(token != null) this.processLogout(token);
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
        this.userService.setLastAccess(user);

        return response;
    }

    protected void processLogout(final String token) {
        this.contextManager.removeToken(token);
    }

    protected void registerToken( final String token, final User user) {
        this.contextManager.addToken(token, user);
    }

}
