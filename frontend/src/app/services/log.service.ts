  import { Injectable } from '@angular/core';
  import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
  import {environment} from "../../environments/environment";
  import {Observable} from "rxjs";
  import {Log} from "../models/log.model";
  import {LogDetail} from "../models/logdetail.model";

@Injectable({
  providedIn: 'root'
})
export class LogService {

  constructor(private http: HttpClient) { }

  public getLogsWithFilter(process: string, startDate: string, endDate: string, status: string, userLogin: string): Observable<any>  {
    console.log(this.setBody(process, startDate, endDate, status, userLogin));
    return this.http.post<Log[]>(`${environment.API}/logs`,
      this.setBody(process, startDate, endDate, status, userLogin)
    , {headers: this.getAuthorizationFromHeader()});
  }

  public getLogDetails(id: number) : Observable<any>{
    return this.http.get<LogDetail[]>(`${environment.API}/logs/details/` + id, {headers: this.getAuthorizationFromHeader()});
  }

  private setBody(process: string, startDate: string, endDate: string, status: string, userLogin: string) : any{
    return {
      process: process && process.length > 0 ? process : null,
      startDate: startDate, //já verifico se é Nulo no "formatDate"
      endDate: endDate,
      status: status && status.length > 0 ? status : null,
      userLogin: userLogin && userLogin.length > 0 ? userLogin : null,
    };
  }

  private getAuthorizationFromHeader() : HttpHeaders {
    let header: HttpHeaders = new HttpHeaders();
    header = header.append("authorization", localStorage.getItem("TOKEN"));
    return header;
  }

}
