import {Component, Inject, OnInit} from '@angular/core';
import {MessageService} from '../../service/message.service';
import {BACKEND_URL, DEV_MODE} from '../../service/backend.service';
 //import {logCumulativeDurations} from '@angular-devkit/build-angular/src/builders/browser-esbuild/profiling';



@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent implements OnInit {
  // heroes: Hero[] = [];

  constructor(private messageService: MessageService,
              @Inject(DEV_MODE) private devMode) { }
  ngOnInit(): void {
    console.log('Dashboard works');
    if (this.devMode) {
      this.messageService.add('Dashboard component initialized');
    }
  }



  getHeroes(): void {
    // this.heroService.getHeroes()
    //   .subscribe(heroes => this.heroes = heroes.slice(1, 5));
    // возвращает 4-х героев: (2-ого, 3-ого, 4-ого и 5-ого).
  }


}
