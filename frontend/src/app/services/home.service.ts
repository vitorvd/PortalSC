import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  constructor(private http: HttpClient) { }

  public getUser() {
    let user = JSON.parse(<string>localStorage.getItem("USER"));
    return user.login;
  }

}
