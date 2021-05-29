import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Title} from "@angular/platform-browser";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  router: Router;

  constructor(router: Router, title: Title){
    this.router = router;
    title.setTitle("Santa Cruz");
  }

}
