import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
  
} from '@angular/router';
import { Observable } from 'rxjs';

import { jwtDecode } from "jwt-decode";

@Injectable({
  providedIn: 'root'
})
export class AllGuard implements CanActivate {

    decoded: any;
  constructor(private router: Router) {
  }
  

  canActivate(
    




    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let permissionsString = localStorage.getItem("token") || '';;


  this.decoded = jwtDecode(permissionsString);


  let permissions=this.decoded.permissions;

      let count=0;

      let vacuumCount=0;

      let lol=[ 'createuser','deleteuser','readuser','updateuser' ]

      let vacuum=[ 'createvacuum','searchvacuum','dischargevacuum','removevacuum','stopvacuum','startvacuum' ]

  permissions.includes('createuser')

  lol.forEach(searchString => {
    if(permissions.includes(searchString)){
      return true;
  }
    else{
      count++;

  return true;
    }

  });
  vacuum.forEach(searchString => {
    if(permissions.includes(searchString)){
      return true;
  }
    else{
      vacuumCount++;

  return true;
    }

  });

  if(count==4 && vacuumCount==6 ){
    alert("You dont have any user and vacuum permissions!")
    return true;
  }

  if(count==4){
    alert("You dont have any user permissions!")
  }

  if(vacuumCount==6){
    alert("You dont have any vacuum permissions!")
  }

    return true;

     


  }

}