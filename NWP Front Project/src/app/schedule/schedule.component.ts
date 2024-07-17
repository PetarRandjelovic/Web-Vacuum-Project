import { Component, OnInit } from '@angular/core';
import { Vacuum } from '../model';
import { DatePipe } from '@angular/common';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { BackendService } from '../service/backendService';

@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.css']
})
export class ScheduleComponent implements OnInit {
  
  vacuum: any = [];
  scheduleStartVacuumForm!: FormGroup;
  scheduleStopVacuumForm!: FormGroup
  scheduleRestartVacuumForm!: FormGroup
  pipe = new DatePipe('en-US');

  constructor(private backendService: BackendService, private router: Router, private route: ActivatedRoute, private formBuilder: FormBuilder) {
    this.scheduleStartVacuumForm = this.formBuilder.group({
      date: Date
    });

    this.scheduleStopVacuumForm = this.formBuilder.group({
      date: Date
    });

    this.scheduleRestartVacuumForm = this.formBuilder.group({
      date: Date
    });
  }

  ngOnInit(): void {
    this.getById()
  }

  getById(){
    this.backendService.getVacuumById(parseInt(<string>this.route.snapshot.paramMap.get('id')))
      .subscribe(result => {
        this.vacuum = result;
        console.log(result)
      })
  }

  scheduleStartVacuum() {

    let date = this.scheduleStartVacuumForm.get('date')?.value;
    let dateString = this.pipe.transform(date, "dd.MM.yyyy HH:mm");
    if(dateString === null){
      alert("Select a date")
      return
    }
    this.backendService.scheduleStart(this.vacuum.id, dateString).subscribe( result => {
 
    })

  }

  scheduleStopVacuum() {
    console.log("Stop")
    let date = this.scheduleStopVacuumForm.get('date')?.value;
    let dateString = this.pipe.transform(date, "dd.MM.yyyy HH:mm");
    if(dateString === null){
      alert("Select a date")
      return
    }
    this.backendService.scheduleStop(this.vacuum.id, dateString).subscribe( result => {
    
    })
  }

  scheduleRestartVacuum() {
    let date = this.scheduleRestartVacuumForm.get('date')?.value;
    let dateString = this.pipe.transform(date, "dd.MM.yyyy HH:mm");
    if(dateString === null){
      alert("Select a date")
      return
    }
    this.backendService.scheduleRestart(this.vacuum.id, dateString).subscribe( result => {
 
    })
  }
}
