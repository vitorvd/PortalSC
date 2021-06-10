import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {UserQuery} from "../models/user-query.model";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserQueryService {

  constructor(private http: HttpClient) { }

  public getAllUserWithFilter(login: string, perfil: string, status: string, createdAt: string, untilAt: string): Observable<any> {
    return this.http.post<UserQuery[]>(`${environment.API}/users`,
      this.setBodyFilter(login, perfil, status, createdAt, untilAt),
      {headers: this.getAuthorizationFromHeader()});
  }

  public saveNewUser(perfil: string, login: string, email: string, status: string, empresa: string): Observable<any> {
    return this.http.post(`${environment.API}/users/new`,
      this.setBodyNew(perfil, login, email, status, empresa),
      {headers: this.getAuthorizationFromHeader()});
  }

  public deleteUser(id: number): Observable<any> {
    return this.http.delete(`${environment.API}/users/delete/` + id,
      {headers: this.getAuthorizationFromHeader()});
  }

  public setBodyNew(perfil: string, login: string, email: string, status: string, empresa: string) {
    return {
      perfilType: perfil,
      login: login,
      email: email,
      status: status,
      company: empresa,
    }
  }

  public setBodyFilter(login: string, perfil: string, status: string, createdAt: string, untilAt: string) {
    return {
      login: login && login.length > 0 ? login : null,
      perfil: perfil,
      status: status,
      createdAt: createdAt,
      untilAt: untilAt
    }
  }

  private getAuthorizationFromHeader() : HttpHeaders {
    let header: HttpHeaders = new HttpHeaders();
    header = header.append("authorization", localStorage.getItem("TOKEN"));
    return header;
  }

}
