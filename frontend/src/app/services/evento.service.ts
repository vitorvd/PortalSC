import { Injectable } from '@angular/core';
import {ReplaySubject} from "rxjs";
import {LogDetail} from "../models/logdetail.model";
import {Log} from "../models/log.model";
import {UserQuery} from "../models/user-query.model";

@Injectable({
  providedIn: 'root'
})
export class EventoService {

  log$: ReplaySubject<Log> = new ReplaySubject<Log>();
  logDetail$: ReplaySubject<LogDetail[]> = new ReplaySubject<LogDetail[]>();

  userEdit$: ReplaySubject<UserQuery> = new ReplaySubject<UserQuery>();

  constructor() { }
}
