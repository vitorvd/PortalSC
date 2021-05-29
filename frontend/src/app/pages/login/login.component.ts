import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginService} from "../../services/login.service";
import {User} from "../../models/user.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  authForm: FormGroup;

  constructor(private fb: FormBuilder, private loginService: LoginService, private router: Router) { }

  ngOnInit(): void {
    this.generateForm();
  }

  private generateForm(): void {
    this.authForm = this.fb.group({
      login: ['', [Validators.required, Validators.min(4)]],
      password: ['', [Validators.required, Validators.min(4)]],
    });
  }

  checkValidTouched(campo: string) {
    return !this.authForm.get(campo).valid && this.authForm.get(campo).touched;
  }

  applyCssError(campo: string) {
    return {
      'has-error': this.checkValidTouched(campo)
    }
  }

  onSubmit() {
    if(this.authForm.valid) {
      this.loginService.authentication(this.authForm.get("login").value, this.authForm.get("password").value).subscribe(value => {
        const user: User = new User(value.id, value.login, value.token);
        localStorage.setItem("TOKEN", value.token);
        localStorage.setItem("USER", JSON.stringify(user));
        this.router.navigateByUrl("/portal");
      });
    }
  }
}
