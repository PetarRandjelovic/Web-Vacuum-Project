import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { BackendService } from '../service/backendService';
import { Vacuum } from '../model';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-vacuum-search',
  templateUrl: './vacuum-search.component.html',
  styleUrls: ['./vacuum-search.component.css']
})
export class VacuumSearchComponent  implements OnInit{

  vacuum: any = [];
  searchVacuumForm: FormGroup;
 

  constructor(private formBuilder: FormBuilder, private backendService: BackendService,private cdr: ChangeDetectorRef) {
    this.searchVacuumForm = this.formBuilder.group({
      vacuumName: '',
      dateFrom: '',
      dateTo: '',
      
    });
  }

  ngOnInit(): void {
   // this.backendService.getAllVacuum

    this.getAllVacuum();
   
  }

  getAllVacuum(){
    this.backendService.getAllVacuum(null, null, null, null)
      .subscribe(result => {
        this.vacuum = result;

        console.log(this.vacuum[0])
      });
  }

  searchVacuum() {
    let statusArray = []

    let elements = (<HTMLInputElement[]><any>document.getElementsByClassName("form-check-input"));

    for (let i = 0; i < elements.length; i++) {
      if (elements[i].type == "checkbox") {
        if (elements[i].checked){
          let value = elements[i].value;
          if(value === 'OFF') {
            statusArray.push("OFF");
          } else {
            statusArray.push("ON");
          }
        }
      }
    }

    console.log("omegalol")
    console.log(this.searchVacuumForm.get('dateFrom')?.value,this.searchVacuumForm.get('dateTo')?.value)

if(this.searchVacuumForm.get('vacuumName')?.value=="" && this.searchVacuumForm.get('dateFrom')?.value=="" && this.searchVacuumForm.get('dateTo')?.value==="" && statusArray.length==0 ){
  this.getAllVacuum();
  return;
}

   
    this.backendService.searchVacuum(



      this.searchVacuumForm.get('vacuumName')?.value,
      statusArray.length === 0 ? null : statusArray,
      this.searchVacuumForm.get('dateFrom')?.value,
      this.searchVacuumForm.get('dateTo')?.value
    ).subscribe( {
      next: value => {
        this.vacuum = value;
      },
      error: err => {
        alert(err);
      }
    });
  }

  clearForm() {
    window.location.reload()
    /*
    this.searchVacuumForm.reset();
    let elements = (<HTMLInputElement[]><any>document.getElementsByClassName("form-check-input"));
    for (let i = 0; i < elements.length; i++) {
      elements[i].checked = false;
    }*/


  }

  startVacuum(id: number) {
    this.backendService.startVacuum(id)
      .subscribe({
        next: value => {
          console.log(value)
        },
        error: err => {
          alert(err?.body);
        }
      });
    setTimeout(function() {
      window.location.reload()
    }, 15001);
  }
  removeVacuum(id: number) {
    this.backendService.removeVacuum(id)
      .subscribe({
        next: value => {
          console.log(value)
        },
        error: err => {
          alert(err?.body);
        }
      });
      window.location.reload()
  }
  stopVacuum(id: number) {
    this.backendService.stopVacuum(id)
      .subscribe({
        next: value => {
          console.log(value)
        },
        error: err => {
          alert(err?.body);
        }
      });
    setTimeout(function() {
      window.location.reload()
    }, 15001);
  }

  dischargeVacuum(id: number) {
    
 this.backendService.getVacuumById(id);
    this.backendService.dischargeVacuum(id)
      .subscribe({
        next: value => {
         
          console.log(value)
        },
        error: err => {
          alert(err?.body);
        }
      });


      setTimeout(() => {
        console.log("Reload 1 activated after 15 seconds");

    this.getAllVacuum();
    this.cdr.detectChanges();
   // window.location.reload();
      }, 15001);
    
      setTimeout(() => {
        console.log("Reload 2 activated after 30 seconds");
   
      this.getAllVacuum();
      //  this.cdr.detectChanges();
      window.location.reload();
      }, 35002);

      // First Timer (starts right away)

    
  }

}
