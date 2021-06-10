package br.com.santacruz.portal.endpoint;

import br.com.santacruz.portal.enums.PerfilType;
import br.com.santacruz.portal.enums.Status;
import br.com.santacruz.portal.fachada.UserService;
import br.com.santacruz.portal.modelo.Log;
import br.com.santacruz.portal.modelo.User;
import br.com.santacruz.portal.modelo.dto.LogDTO;
import br.com.santacruz.portal.modelo.dto.UserQueryDTO;
import br.com.santacruz.portal.modelo.dto.UserQueryResponseDTO;
import br.com.santacruz.portal.modelo.dto.UserRegisterDTO;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Path("users")
public class UserEndPoint {

    @Autowired
    private UserService service;
    private Long id;

    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity register(@RequestBody UserRegisterDTO dto) {
        LocalDateTime now = LocalDateTime.now();

        User user = User.builder()
                .login(dto.getLogin())
                .email(dto.getEmail())
                .passwordHash(BCrypt.hashpw("teste", BCrypt.gensalt(10)))
                .perfilType(PerfilType.valueOf(dto.getPerfilType()))
                .status(Status.valueOf(dto.getStatus()))
                .company(dto.getCompany())
                .createdAt(now)
                .updatedAt(now).build();

        boolean userSaved = service.save(user);

        if(userSaved) return ResponseEntity.status(HttpStatus.ACCEPTED).body(dto.getLogin() + " registrado! Enviamos um e-mail para " + dto.getEmail() + " com os dados de autenticação.");

        return ResponseEntity.status(HttpStatus.IM_USED.value()).body(dto.getLogin() + " já registrado!");
    }

    @DELETE
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity register(@PathParam("id") Long id) {
        Optional<User> userDeleted = service.delete(id);

        if(!userDeleted.isPresent()) return ResponseEntity.status(HttpStatus.CONFLICT).body(userDeleted.get().getLogin() + " não encontrado.");

        return ResponseEntity.status(HttpStatus.ACCEPTED.value()).body(userDeleted.get().getLogin() + " excluído.");
    }

    @PUT
    @Path("/edit/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity update(@PathParam("id") Long id, @RequestBody UserRegisterDTO dto) {
        boolean isUpdated = this.service.update(id, dto);

        if(!isUpdated) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");

        return ResponseEntity.status(HttpStatus.ACCEPTED.value()).body("Usuário atualizado.");
    }

    @GET
    @Path("/edit/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity findUser(@PathParam("id") Long id) {
        Optional<User> userOptional = this.service.findUser(id);
        if(!userOptional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");

        User user = userOptional.get();

        UserQueryResponseDTO userDTO = UserQueryResponseDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .email(user.getEmail())
                .perfil(user.getPerfilType().name())
                .status(user.getStatus().name())
                .company(user.getCompany())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt()).build();

        return ResponseEntity.status(HttpStatus.FOUND).body(userDTO);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUserByFilter(UserQueryDTO dto) {
        //Pageable pageable = PageRequest.of(0, 5);
        List<UserQueryResponseDTO> list = this.service.getAllUserByFilter(dto)
                .stream().map(userTemp -> UserQueryResponseDTO.builder()
                        .id(userTemp.getId())
                        .login(userTemp.getLogin())
                        .perfil(userTemp.getPerfilType().name())
                        .status(userTemp.getStatus().name())
                        .company(userTemp.getCompany())
                        .createdAt(userTemp.getCreatedAt())
                        .updatedAt(userTemp.getUpdatedAt()).build()).collect(Collectors.toList());

        return Response.ok(list).build();
    }

}
