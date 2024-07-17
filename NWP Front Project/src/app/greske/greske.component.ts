import { Component, OnInit } from '@angular/core';
import { BackendService } from '../service/backendService';
import { ErrorMessage } from '../model';

@Component({
  selector: 'app-greske',
  templateUrl: './greske.component.html',
  styleUrls: ['./greske.component.css']
})
export class GreskeComponent implements OnInit {

  errors: ErrorMessage[]=[];

 

  constructor(private backendService: BackendService) { }

  ngOnInit(): void {
    this.getAllErrors();
  }

  getAllErrors(){
    this.backendService.getAllErrors()
      .subscribe(result => {
        this.errors = result;
   
      })
      console.log(this.errors)
  }

}
