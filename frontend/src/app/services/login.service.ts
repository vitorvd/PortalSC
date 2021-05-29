import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient, private router: Router) { }

  public authentication(login : string, password: string): Observable<any> {
    return this.http.post(`${environment.API}/auth`, {}, {headers: this.getHeaders(login, password)});
  }

  public logout(){
    localStorage.removeItem("TOKEN");
    localStorage.removeItem("USER");
    this.router.navigateByUrl("/auth");
  }

  private getHeaders(login: string, password: string) : HttpHeaders {
    let header: HttpHeaders = new HttpHeaders();
    header = header.append("login", login);
    header = header.append("password", password);

    return header;
  }

}
