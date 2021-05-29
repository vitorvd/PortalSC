export class User {

  id: number;
  login: string;
  token: string;

  constructor(id, login, token) {
    this.id = id;
    this.login = login;
    this.token = token;
  }

}
