import { BrowserModule } from '@angular/platform-browser';
import {LOCALE_ID, NgModule} from '@angular/core';

import { AppComponent } from './app.component';
import { HeaderComponent } from './pages/header/header.component';
import {AppRoutingModule} from './app-routing.module';
import { HeaderTextComponent } from './pages/header-text/header-text.component';
import { LoginComponent } from './pages/login/login.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import { HomeComponent } from './pages/home/home.component';
import { ConsultarLogComponent } from './pages/consultar-log/consultar-log.component';
import {CalendarModule} from 'primeng/calendar';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {TableModule} from "primeng/table";
import { LogDetailComponent } from './pages/log-detail/log-detail.component';
import {BreadcrumbModule} from "primeng/breadcrumb";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HeaderTextComponent,
    LoginComponent,
    HomeComponent,
    ConsultarLogComponent,
    LogDetailComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    CalendarModule,
    TableModule,
    BreadcrumbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
