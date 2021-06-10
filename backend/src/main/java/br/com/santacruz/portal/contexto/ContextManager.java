package br.com.santacruz.portal.contexto;

import br.com.santacruz.portal.modelo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ContextManager {

    private static final String MANAGER_CONTEXT = "CONTEXT.SANTACRUZ"; //nome do atributo definido na requisição

    @Autowired
    private ServletContext servletContext;

    @PostConstruct
    public void init(){
        this.servletContext.setAttribute(MANAGER_CONTEXT, new HashMap<String, Object>());
    }
    
    public void addToken(String token, Object user) {
        this.getMap().put(token, user);
    }

    public void removeToken(String token) {
        //ele pode ter multiplas sessões, por isso é interessante armazenar todas elas
        //o token virá de alguma parte da requisição (provavelmente o Header)
        List<String> tokens = this.getMap().entrySet().stream()
                .filter(tokenTemp -> (tokenTemp.getKey()).equals(token))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        tokens.forEach(tokenTemp -> getMap().remove(tokenTemp));
    }

    public boolean hasToken(String token){
        return this.getMap().containsKey(token);
    }

    public User getUserByToken(String token) {
        return (User) this.getMap().get(token);
    }

    private Map<String, Object> getMap() {
        return (Map<String, Object>) this.servletContext.getAttribute(MANAGER_CONTEXT);
    }

}
