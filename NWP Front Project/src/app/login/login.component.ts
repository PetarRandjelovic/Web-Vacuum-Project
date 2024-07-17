import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import { Router } from '@angular/router';
import { BackendService } from '../service/backendService';
import { AuthService } from '../service/authService';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  template: `
  <button (click)="refreshPage()">Refresh Page</button>
`,
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
 

  constructor(private backendService: BackendService, private formBuilder: FormBuilder, private router: Router,private authService: AuthService) {
    this.loginForm = this.formBuilder.group({
      email: '',
      password: ''
    });
  }

  ngOnInit(): void {
    this.backendService.savedToken=localStorage.getItem("token")
  }

  login() {
 
    console.log(this.loginForm.get('password')?.value)
    this.backendService.login(this.loginForm.get('email')?.value, this.loginForm.get('password')?.value
    ).subscribe({
      next: res => {
      
    
        localStorage.setItem('token', res.jwt)
    
        localStorage.setItem('permissions', JSON.stringify(res.permissionm))

        this.backendService.savedToken=localStorage.getItem('token')
      
        this.authService.setAuthenticated(true);
        this.backendService.changedEventToken.emit()

        this.router.navigate(["/welcome"])
        
      
       
      },
      error: err => {
           alert(`Error ${err.status}. Wrong login.`)
      }
    }

    )

  }
}
