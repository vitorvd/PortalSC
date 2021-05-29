import {Component, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {MenuItem} from "primeng/api";

@Component({
  selector: 'app-header-text',
  templateUrl: './header-text.component.html',
  styleUrls: ['./header-text.component.scss']
})
export class HeaderTextComponent implements OnInit {

  @Input() text: string = "";
  @Input() route: string = "";
  @Input() items: MenuItem[];

  router: Router;

  constructor(router: Router){
    this.router = router;
  }

  ngOnInit(): void {
  }

  public getText() {
    return this.text;
  }

  public getRoute() {
    return this.route;
  }

  public getItems() {
    return this.items;
  }

}
