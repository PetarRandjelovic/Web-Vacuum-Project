import { Component, EventEmitter, OnInit } from '@angular/core';
import { AuthService } from './service/authService';
import { NavigationEnd, Router } from '@angular/router';
import { BackendService } from './service/backendService';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  template: `
  <button (click)="refreshPage()">Refresh Page</button>
`,
})
export class AppComponent  implements OnInit {
  title = 'NWPDomaci3Front';
  isAuthenticated: boolean = false;
  users: any = [];
  shouldShowLogin: boolean = false;

  constructor(private backendService: BackendService,private authService: AuthService,private router: Router) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        // Check the current URL path
        const currentPath = this.router.url;

        // Do something based on the current URL path
        if (currentPath.includes('/login')) {
          this.shouldShowLogin=false
        //  this.backendService.changedEventToken.emit()
      //    console.log('Current path contains /login');
          // Perform actions specific to the /login route
      //    console.log( localStorage.getItem('token'))
       //   localStorage.removeItem('token');
          
       //   console.log( localStorage.getItem('token'))
        } else {
          this.getAllUsers()
          this.backendService.savedToken=localStorage.getItem("token")
          this.backendService.changedEventToken.emit()
          this.shouldShowLogin=true
          console.log('Current path does not contains /login');
         // console.log( localStorage.getItem('token'))
        
        }
      }
    });

  }
  ngOnInit(): void {
    this.authService.isAuthenticated$.subscribe((isAuthenticated) => {
      if (isAuthenticated) {
        // Logic to execute when a user logs in
   //     console.log('User logged in!');
        // Additional logic...
       
      } else {
    //    console.log('User logged out!');
    // console.log( localStorage.getItem('token'))
    
    // localStorage.removeItem('token');
    // console.log( localStorage.getItem('token'))
      }
    });
  }
    
logout() {

  if(this.isAuthenticated){

    console.log("login")

 // localStorage.removeItem('token');
 // this.authService.setAuthenticated(false);
  }else{
  
    console.log("logout")
    localStorage.removeItem('token');
//  this.authService.setAuthenticated(false);
  // Additional logout logic...
  }

}

  getAllUsers(){
    this.backendService.getAllUsers()
      .subscribe(
        
        result => {
          
        this.users = result;
      })
  }



}
 
