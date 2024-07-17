import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { BackendService } from '../service/backendService';

@Component({
  selector: 'app-create-vacuum',
  templateUrl: './create-vacuum.component.html',
  styleUrls: ['./create-vacuum.component.css']
})
export class CreateVacuumComponent implements OnInit{

  newVacuumForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private backendService: BackendService) {
    this.newVacuumForm = this.formBuilder.group({
      vacuumName: ''
    });
  }

  addVacuum(){
console.log(localStorage.getItem("token"))

    this.backendService.addVacuum(
      
      this.newVacuumForm.get('vacuumName')?.value
    ).subscribe( {
      next: res => {
        alert("Vacuum successfully created.")
      },
      error: err => {
        alert(err);
      }
    });
  }

  ngOnInit(): void {
  }

}
