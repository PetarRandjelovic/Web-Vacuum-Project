import { Component } from '@angular/core';
import { User } from 'src/app/model';
import { BackendService } from '../service/backendService';
import { Router } from '@angular/router';

import { jwtDecode } from "jwt-decode";
import { Token } from '@angular/compiler';



@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent {

  users: any = [];
  decoded: any;



  constructor(private backendService: BackendService, private router: Router) { }




  ngOnInit(): void {
    this.backendService.savedToken=localStorage.getItem("token")
    console.log("user")
    this.getAllUsers()
  }

  getAllUsers(){
    this.backendService.getAllUsers()
      .subscribe(
        
        result => {
         

    //     localStorage.setItem('token', ressult.jwt)
    
   //       localStorage.setItem('permissions', JSON.stringify(result.permissionm))


        this.users = result;
      })
  }

  navigateToUser(id: number){
    this.router.navigate([`/users/${id}`])
  }

  checkPermission(p: string){
    let token = localStorage.getItem("token") || '';


    this.decoded = jwtDecode(token);
    

    let permissions=this.decoded.permissions;

  
    if(token != null){

      for(let permission of permissions) {

        if (permission === p) {
          return true;
        }
      }

    }
    return false;
  }

  deleteUser(id: number){

    this.backendService.deleteUser(String(id))
      .subscribe(result=> {
        console.log(result)
        alert(`User with id ${id} deleted.`)
        this.getAllUsers()
      }); 
  }
  editUser(id: number){

    
    this.router.navigate([`/edituser/${id}`]);
  }

}
