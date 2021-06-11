import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {MenuItem, MessageService} from "primeng/api";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Profile} from "../../models/profile";
import {Status} from "../../models/status";
import {UserQueryService} from "../../services/user-query.service";
import {take, takeUntil} from "rxjs/operators";
import {Company} from "../../models/company";
import {UserQuery} from "../../models/user-query.model";
import {Router} from "@angular/router";
import {Subscription} from "rxjs";
import {EventoService} from "../../services/evento.service";

@Component({
  selector: 'app-criar-user',
  templateUrl: './criar-user.component.html',
  styleUrls: ['./criar-user.component.scss'],
  providers: [MessageService]
})
export class CriarUserComponent implements OnInit {

  private text: string = "Registro de Usuário";
  private items: MenuItem[] = [
    {label: 'Página Inicial', url: '/portal'},
    {label: 'Cadastros', url: '/portal/users'},
    {label: 'Usuários', url: '/portal/users'},
    {label: 'Novo', url: '/portal/users/new'}
  ]

  private formUser: FormGroup;

  private profiles: Profile[];
  private selectedProfile: Profile;

  private status: Status[];
  private selectedStatus: Status;

  private company: Company[];
  private selectedCompany: Company;

  private isAd: boolean;

  public showProgessBar: boolean = false;

  private userEditSubscription: Subscription;

  constructor(private router: Router,
              private formBuilder: FormBuilder,
              private service: UserQueryService,
              private messageService: MessageService,
              private eventService: EventoService) {
    this.profiles = [
      {name: 'Administrador'},
      {name: 'Suporte'}
    ];
    this.status = [
      {name: 'Ativo'},
      {name: 'Inativo'}
    ];
    this.company = [
      {name: 'SantaCruz'},
      {name: 'Panpharma'}
    ]
  }

  ngOnInit() {
    this.generateForm();
    this.userEditSubscription = this.eventService.userEdit$.subscribe((value: UserQuery) => {
      this.formUser.controls['id'].setValue(value.id);
      this.selectedProfile = {name: value.perfil};
      this.formUser.controls['login'].setValue(value.login);
      this.formUser.controls['email'].setValue(value.email);
      this.selectedStatus = {name: value.status};
      this.selectedCompany = {name: value.company};
    })
  }

  public onSubmit() {
    if(this.formUser.valid) {
      this.showProgessBar = true;

      let {id, profile, login, email, status, company} = this.formUser.value;

      if(id === ''){
        this.messageService.add({severity:'warn', summary:'Registrando Usuário...', detail: 'Estamos enviando um e-mail, aguarde...', life: 3000});
        this.service.saveNewUser(profile.name, login, email, status.name, company.name)
          .pipe(take(1))
          .subscribe((value) => {
            let type: string = value.statusCode;
            this.showProgessBar = false;
            if(type.startsWith("IM_USED")){
              this.messageService.add({severity:'error', summary:'Usuário já registrado', detail: value.body, life: 4000});
            }else{
              this.messageService.add({severity:'success', summary:'Usuário registrado', detail: value.body, life: 5000});
              this.formUser.reset();
            }
        });
      }else{

        this.service.updateUser(id, login, email, profile.name, status.name, company.name)
          .pipe(take(1))
          .subscribe((value) => {
            let type: string = value.statusCode;
            this.showProgessBar = false;
            if (type.startsWith("NOT_FOUND")) {
              this.messageService.add({
                severity: 'error',
                summary: 'Usuário não encontrado',
                detail: value.body,
                life: 4000
              });
            } else {
              this.messageService.add({
                severity: 'success',
                summary: 'Usuário Encontrado',
                detail: value.body,
                life: 4000
              });
              this.formUser.reset();
            }
          });

      }
    }else{
      this.messageService.add({severity:'error', summary:'Algo inesperado...', detail: 'Algo fora dos padrões no formulário de registro! Verifique novamente.', life: 3000});
    }
  }

  private generateForm(): void {
    this.formUser = this.formBuilder.group({
      id: [''],
      profile: ['', [Validators.required]],
      login: ['', [Validators.required, Validators.min(4)]],
      email: ['', [Validators.required, Validators.email]],
      status: ['', [Validators.required]],
      company: ['', [Validators.required]],
      ad: [''],
    })
  }
}
