package br.com.santacruz.portal.security;

import br.com.santacruz.portal.contexto.ContextManager;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Priority;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.annotation.Annotation;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {

    @Autowired
    private ContextManager contextManager;

    @Context
    protected HttpServletRequest servletRequest;

    @Context
    protected ResourceInfo resourceInfo;

    @SneakyThrows
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        if (!this.annotationIsPresent(PermitAll.class) && !containerRequestContext.getMethod().equalsIgnoreCase("OPTIONS")) {
            final String token = containerRequestContext.getHeaderString("authorization"); //pega o Token do Header da requisição

            if (token == null || !this.contextManager.hasToken(token)) {
                throw new Exception("Você precisa estar autenticado para acessar essa rota.");
            }

            this.servletRequest.setAttribute("TOKEN_USER_AUTHORIZATION", token);
            this.servletRequest.setAttribute("TOKEN_USER", this.contextManager.getUserByToken(token));
        }
    }

    /*
    * getResourceMethod() = o retorno do Response.ok irá vir de um método - que estará na class Pai (ex.: SuperResource)
    * getResourceClass() = o retorno irá vir da class filha (ex.: MyResource) - que terá o método da class Pai
    * */
    protected boolean annotationIsPresent(Class<? extends Annotation> annotationClass) {
        return this.resourceInfo.getResourceMethod().isAnnotationPresent(annotationClass) || this.resourceInfo.getResourceClass().isAnnotationPresent(annotationClass);
    }

}
