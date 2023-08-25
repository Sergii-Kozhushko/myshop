import {Component, Input, OnInit} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-top-nav',
  templateUrl: './top-nav.component.html',
  styleUrls: ['./top-nav.component.css']
})
export class TopNavComponent implements OnInit{
  @Input() selectedMenu: string = 'dashboard';


  constructor(private router: Router) {

  }

  ngOnInit(): void {
  }


}
