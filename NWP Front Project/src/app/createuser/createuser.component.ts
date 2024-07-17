import { Component } from '@angular/core';
import { BackendService } from '../service/backendService';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../model';
import { emailValidator } from '../validator/email.validator';

@Component({
  selector: 'app-createuser',
  templateUrl: './createuser.component.html',
  styleUrls: ['./createuser.component.css']
})
export class CreateuserComponent {
  createUserForm: FormGroup;
  checkForm: FormGroup;
  user!: User; 
 
  cancreate: boolean = false;
  canupdate: boolean = false;
  canread: boolean = false;
  candelete: boolean = false;
  permissions:string[]=[];
  permissionMap = new Map<string, boolean>();

  constructor(private backendService: BackendService, private formBuilder: FormBuilder, private router: Router, private route: ActivatedRoute) {
    this.createUserForm = this.formBuilder.group({
      myCheckbox: [false],
      username: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, emailValidator]],
      password: ['', Validators.required],
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
  }

  togglePermission(permission:string){
    console.log("togglePermission:"+permission);
    let permissionValue = this.permissionMap.get(permission);
    this.permissionMap.set(permission,!permissionValue);

 
  }
  
  createUser(){

  
   
   
    if(this.createUserForm.get('username')?.value=="" || this.createUserForm.get('lastName')?.value==""
    || this.createUserForm.get('email')?.value=="" || this.createUserForm.get('password')?.value=="" ){
      alert("Text field can not be empty!");
    
    }
  else{
    this.backendService.createUser(
    
  
    
      this.createUserForm.get('username')?.value,
      this.createUserForm.get('lastName')?.value,
      this.createUserForm.get('email')?.value,
    
      this.permissionMap.get("can_create_users"),
      this.permissionMap.get("can_update_users"),
      this.permissionMap.get("can_read_users"),
      this.permissionMap.get("can_delete_users"),
      this.createUserForm.get('password')?.value,

    ).subscribe(result => {
      console.log(result)
      console.log("create")
      alert("Successfully updated");

      if (result.status === 400) {
        // Handle the specific case where the email is not unique
        console.log('User with this email already exists');
      }

      this.router.navigate(['/users']);
    });

  }
  
  }



}
