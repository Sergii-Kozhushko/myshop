import {Component, OnInit} from '@angular/core';
import {SpinnerService} from '../../../oauth2/spinner/spinner.service';


@Component({
  selector: 'app-mainpage',
  templateUrl: './mainpage.component.html',
  styleUrls: ['./mainpage.component.css']
})
export class MainpageComponent implements OnInit{
  spinner: SpinnerService;

  constructor(private spinnerService: SpinnerService) {

  }

  ngOnInit(): void {
    this.spinner = this.spinnerService;
  }

}
