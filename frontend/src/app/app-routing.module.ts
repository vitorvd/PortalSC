import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from './pages/login/login.component';
import {HomeComponent} from './pages/home/home.component';
import {ConsultarLogComponent} from './pages/consultar-log/consultar-log.component';
import {Guard} from './guard/Guard';
import {LogDetailComponent} from "./pages/log-detail/log-detail.component";

const routes: Routes = [
  {path: "", redirectTo: "/auth", pathMatch: "full"},
  {path: "auth", component: LoginComponent},
  {path: "portal", component: HomeComponent, canActivate: [Guard]},
  {path: "portal/logs", component: ConsultarLogComponent, canActivate: [Guard]},
  {path: "portal/logs/details", component: LogDetailComponent, canActivate: [Guard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
