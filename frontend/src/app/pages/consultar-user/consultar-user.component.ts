import { Component, OnInit } from '@angular/core';
import {ConfirmationService, MenuItem, MessageService} from "primeng/api";
import {Profile} from "../../models/profile";
import {Form, FormBuilder, FormGroup} from "@angular/forms";
import {Status} from "../../models/status";
import {Log} from "../../models/log.model";
import {UserQuery} from "../../models/user-query.model";
import {UserQueryService} from "../../services/user-query.service";
import {take} from "rxjs/operators";

@Component({
  selector: 'app-consultar-user',
  templateUrl: './consultar-user.component.html',
  styleUrls: ['./consultar-user.component.scss'],
  providers: [MessageService, ConfirmationService]
})
export class ConsultarUserComponent implements OnInit {

  private text: string = "Consulta de Usuário";
  private items: MenuItem[] = [
    {label: 'Página Inicial', url: '/portal'},
    {label: 'Cadastros', url: '/portal/users'},
    {label: 'Usuários', url: '/portal/users'}
  ]

  private filterForm: FormGroup;
  private profiles: Profile[];
  private status: Status[];

  private users: UserQuery[];

  pt: any;

  constructor(private formBuilder: FormBuilder,
              private service: UserQueryService,
              private messageService: MessageService,
              private confirmationService: ConfirmationService) {

    this.profiles = [
      {name: 'Administrador'},
      {name: 'Suporte'}
    ];

    this.status = [
      {name: 'Ativo'},
      {name: 'Inativo'}
    ];

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

  ngOnInit() {
    this.generateForm();
  }

  public onSubmit() {
    if(this.filterForm.valid) {
      let {profile, createdAt, untilAt, status, login} = this.filterForm.value;
      let created: Date = createdAt;
      let until: Date = untilAt;
      let profileName: string = typeof(profile) == "undefined" || profile == null ? null : profile.name;
      let statusName: string =typeof(status) == "undefined" || status == null ? null : status.name;
      this.service.getAllUserWithFilter(login, profileName, statusName, this.formatDate(created, false), this.formatDate(until, true))
        .subscribe((value: UserQuery[]) => {
          this.users = value;
        });
    }
  }

  public cofirmDialogToDelete(user: string, id: number) {
    this.confirmationService.confirm({
      header: 'Confirmação',
      icon: 'pi pi-exclamation-triangle',
      message: 'Você tem certeza que deseja excluir o usuário: ' + user + '? Caso aceite, não será possível recuperá-lo!',
      accept: () => {
        this.deleteUser(id);
      }
    });
  }

  public deleteUser(id: number){
    this.service.deleteUser(id)
      .pipe(take(1))
      .subscribe((value => {
        let type: string = value.statusCode;
        if(type.startsWith("CONFLICT")){
          this.messageService.add({severity:'error', summary:'Usuário não encontrado', detail: value.body, life: 4000});
        }else{
          this.messageService.add({severity:'success', summary:'Usuário Deletado', detail: value.body, life: 5000});
          this.onSubmit();
        }
      }))
  }

  private generateForm(): void {
    this.filterForm = this.formBuilder.group({
      profile: [''],
      createdAt: [''],
      untilAt: [''],
      status: [''],
      login: ['']
    })
  }

  private formatDate(date: Date, until: boolean): any {
    if(!date) return null;
    let day: any = date.getDate();
    let month = date.getMonth() + 1;
    let year = date.getFullYear();
    let monthStr = "";
    let time = " 00:00:00";

    if(until) {
      time = " 23:59:00"
    }

    if(date.getDate() <= 9) {
      day = "0" + date.getDate();
    }

    if(month <= 9) {
      monthStr = "0" + month;
      return day + "/" + monthStr + "/" + year + time;
    }

    return day + "/" + month + "/" + year + time;
  }

}
