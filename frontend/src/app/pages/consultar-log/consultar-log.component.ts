import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Log} from "../../models/log.model";
import {LogService} from "../../services/log.service";
import {LogDetail} from "../../models/logdetail.model";
import {Router} from "@angular/router";
import {EventoService} from "../../services/evento.service";
import {MenuItem} from "primeng/api";

@Component({
  selector: 'app-consultar-log',
  templateUrl: './consultar-log.component.html',
  styleUrls: ['./consultar-log.component.scss']
})
export class ConsultarLogComponent implements OnInit {

  private text: string = "Consultar Logs";
  private items: MenuItem[] = [
    {label: 'Página Inicial', url: '/portal'},
    {label: 'Logs', url: '/portal/logs'}
  ]

  filterForm: FormGroup;
  logs: Log[] = [];
  pt: any;

  constructor(private fb: FormBuilder, private logService: LogService,
              private eventoService: EventoService,
              private router: Router) {

    this.pt = {
      firstDayOfWeek: 0,
      dayNames: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'],
      dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'],
      dayNamesMin: ['Do', 'Se', 'Te', 'Qu', 'Qu', 'Se', 'Sa'],
      monthNames: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho',
        'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
      monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
      today: 'Hoje',
      clear: 'Limpar'
    };
  }

  ngOnInit(): void {
    this.generateForm();
  }

  onSubmit() {
    if(this.filterForm.valid) {
      let {process, startDate, endDate, status, user} = this.filterForm.value;
      let start: Date = startDate;
      let end: Date = endDate;
      this.logService.getLogsWithFilter(process, this.formatDate(start), this.formatDate(end), status, user).subscribe((log: Log[]) => {
          this.logs = log;
      });
    }
  }

  public getDetails(log: Log) {
    this.logService.getLogDetails(log.id).subscribe((logDetail: LogDetail[]) => {
      this.eventoService.logDetail$.next(logDetail);
      this.eventoService.log$.next(log);
      this.router.navigateByUrl("/portal/logs/details");
    })
  }

  private formatDate(date: Date): any {
    if(!date) return null;
    let day = date.getDate();
    let month = date.getMonth() + 1;
    let year = date.getFullYear();
    let monthStr = "";

    if(month <= 9) {
      monthStr = "0" + month;
      return year + "-" + monthStr + "-" + day;
    }else{
      return year + "-" + month + "-" + day;
    }
  }

  private generateForm(): void {
    this.filterForm = this.fb.group({
      process: [''],
      status: [''],
      startDate: [''],
      endDate: [''],
      user: ['']
    });
  }
}
