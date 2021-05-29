import { Component, OnInit } from '@angular/core';
import {HomeService} from '../../services/home.service';
import {MenuItem} from "primeng/api";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  private text: string = "Seja bem-vindo, " + this.homeService.getUser();
  private items: MenuItem[] = [
    {label: 'Página Inicial', url: '/portal'}
  ]

  constructor(private homeService: HomeService) { }

  ngOnInit(): void {
  }

}
