import {Component, Injectable, OnInit} from '@angular/core';
import {LogDetail} from "../../models/logdetail.model";
import {EventoService} from "../../services/evento.service";
import {Subscription} from "rxjs";
import {Log} from "../../models/log.model";
import {MenuItem} from "primeng/api";

@Component({
  selector: 'app-log-detail',
  templateUrl: './log-detail.component.html',
  styleUrls: ['./log-detail.component.scss']
})
@Injectable({
  providedIn: 'root',
})
export class LogDetailComponent implements OnInit {

  text: string = "Detalhamento do Log";
  route: string = "Página Inicial » Logs » Detalhes"
  private items: MenuItem[] = [
    {label: 'Página Inicial', url: '/portal'},
    {label: 'Logs', url: '/portal/logs'},
    {label: 'Detalhes', url: '/portal/logs/details'}
  ]

  log: Log;
  logSubscription: Subscription;

  logDetails: LogDetail[];
  logDetailsSubscription: Subscription;

  constructor(private eventoService: EventoService) { }

  ngOnInit() {
    this.logDetailsSubscription = this.eventoService.logDetail$.subscribe(value => {
      this.logDetails = value;
    })
    this.logDetailsSubscription = this.eventoService.log$.subscribe(value => {
      this.log = value;
    })
  }

}
