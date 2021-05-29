import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class Guard implements CanActivate{

  constructor(private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if(this.userIsLogged()) {
      console.log("[CanActive - Guard] » Usuário autenticado!");
      return true;
    }

    console.log("[CanActive - Guard] » Usuário não autenticado!");
    this.router.navigate(['/auth']);
    return false;
  }

  private userIsLogged(): boolean {
    return localStorage.getItem("USER") != undefined;
  }

}
