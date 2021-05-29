import { Injectable } from '@angular/core';
import {ReplaySubject} from "rxjs";
import {LogDetail} from "../models/logdetail.model";
import {Log} from "../models/log.model";

@Injectable({
  providedIn: 'root'
})
export class EventoService {

  log$: ReplaySubject<Log> = new ReplaySubject<Log>();
  logDetail$: ReplaySubject<LogDetail[]> = new ReplaySubject<LogDetail[]>();

  constructor() { }
}
