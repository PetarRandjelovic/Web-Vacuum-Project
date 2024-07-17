import { Component } from '@angular/core';
import { BackendService } from '../service/backendService';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import { User } from '../model';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-edituser',
  templateUrl: './edituser.component.html',
  styleUrls: ['./edituser.component.css']
})
export class EdituserComponent {

  editUserForm: FormGroup;
  checkForm: FormGroup;
  user!: User; 
 
  cancreate: boolean = false;
  canupdate: boolean = false;
  canread: boolean = false;
  candelete: boolean = false;
  permissions:string[]=[];
  permissionMap = new Map<string, boolean>();

  constructor(private backendService: BackendService, private formBuilder: FormBuilder, private router: Router, private route: ActivatedRoute) {
    this.editUserForm = this.formBuilder.group({
      myCheckbox: [false],
      username: [''],
      lastName: [''],
      email: [''],
      createuser: [''],
      updateuser: [''],
      readuser: [''],
      deleteuser: [''],
      formPermissions: new FormArray([])
    });
    this.permissionMap.set("can_read_users", false);
    this.permissionMap.set("can_create_users", false);
    this.permissionMap.set("can_update_users", false);
    this.permissionMap.set("can_delete_users", false);
    this.checkForm = this.formBuilder.group({
      createuser: false,
      readuser: false,
      updateuser: false,
      deleteuser: false
      // Add other form controls as needed
    });

  }
  ngOnInit(): void {
    this.backendService.savedToken=localStorage.getItem("token")
    this.getById()
 
  }

  togglePermission(permission:string){
    console.log("togglePermission:"+permission);
    let permissionValue = this.permissionMap.get(permission);
    this.permissionMap.set(permission,!permissionValue);

 
  }
  
  updateUser(){

  

 
    let elements = (<HTMLInputElement[]><any>document.getElementsByClassName("form-check-input"));

    if(this.editUserForm.get('username')?.value=="" || this.editUserForm.get('lastName')?.value==""
    || this.editUserForm.get('email')?.value==""  ){
      alert("Text field can not be empty!");
    
    }
  else{
    this.backendService.updateUser(
    
      <string>this.route.snapshot.paramMap.get('id'),
      
     

      this.editUserForm.get('username')?.value,
      this.editUserForm.get('lastName')?.value,
      this.editUserForm.get('email')?.value,
      this.permissionMap.get("can_read_users"),
      this.permissionMap.get("can_delete_users"),
      this.permissionMap.get("can_update_users"),
      this.permissionMap.get("can_create_users"),
      this.user.password

    ).subscribe(result => {
      console.log(result)
      alert("Successfully updated");
      this.router.navigate(['/users']);
    });

  }
  
  }
  getById(){

    this.backendService.getUserById(parseInt(<string>this.route.snapshot.paramMap.get('id')))
      .subscribe(result => {
          
        this.user = result; 

      
   
        this.permissions = result.permissions


        this.editUserForm.patchValue({
          username: this.user.username,
          lastName: this.user.lastName,
          email: this.user.email,
     
        });


      })
  }



}
