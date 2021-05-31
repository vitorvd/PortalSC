# PortalSC
Um projeto desenvolvido para tornar mais maduros meus conhecimentos com o Spring Framework e seu ecossistema, além de colocar em prática os ensinamentos teóricos adquiridos de Angular.

# Tecnologias

| Tecnologia | Descrição |
| --------- | --------- |
| Spring Boot | Responsável pela portábilidade Web |
| Hibernate | ORM. Um auxiliar para manuseio do Banco de Dados |
| Postegres | SGBD |
| Lombook | Gerar Getters e Setters por Annotations |
| BCrypt | Criptografia |
| Angular 2 | Framework frontend |
| SCSS | Pré compilador CSS |
| TypeScript | SuperSettings tipado do JavaScript |

# Como inicializar?

## Postgres (SGBD):
Para o projeto funcionar de forma adequada, será necessário realizar a conexão com o SGBD, neste caso, o Postegres.
Dentro do diretório **"/main/resources"** altere os campos:
> spring.datasource.url: » COLOQUE AQUI A URL
> 
> spring.datasource.username: » COLOQUE AQUI O USERNAME
> 
> spring.datasource.password: » COLOQUE AQUI A SENHA

## Angular 2 (Front-end):
Certifique-se que você esteja dentro do diretório **"/frontend"**, nele você executará o seguinte comando:
> ng server » Aguarde a inicializar e acesse, no navegador, a URL: http://localhost:4200/

## Spring Framework (Back-end):
Procure, no pacote principal do projeto, o arquivo **PortalSantaCruzApplication**, clique com o botão direito e de um **Run**.
