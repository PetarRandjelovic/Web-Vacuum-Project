import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {UserComponent} from "./user/user.component";
import {LoginComponent} from "./login/login.component";
import {EdituserComponent} from "./edituser/edituser.component";
import { CreateuserComponent } from './createuser/createuser.component';
import { ReadGuard } from './read.guard';
import { CreateGuard } from './create.guard';
import { WelcomeComponent } from './welcome/welcome.component';
import { AllGuard } from './all.guard';
import { VacuumSearchComponent } from './vacuum-search/vacuum-search.component';
import { CreateVacuumComponent } from './create-vacuum/create-vacuum.component';
import { GreskeComponent } from './greske/greske.component';
import { ScheduleComponent } from './schedule/schedule.component';
import { createVacuumGuard } from './guard/createVacuum.guard';
import { searchVacuumGuard } from './guard/searchVacuum.guartd';


const routes: Routes = [


  {

    path: "users",
    component: UserComponent,
    
    canActivate: [ReadGuard],
    
},  {

  path: "create-vacuum",
  component: CreateVacuumComponent,
  
  canActivate: [createVacuumGuard],
  
}, {

  path: "schedule/:id",
  component: ScheduleComponent,
  
 
  
},
{

  path: "vacuum-search",
  component: VacuumSearchComponent,
  
  canActivate: [searchVacuumGuard],
  
},
{

  path: "errors",
  component: GreskeComponent,
  
 
  
},
{

  path: "welcome",
  component: WelcomeComponent,
  canActivate: [AllGuard],
},

{

  path: "login",
  component: LoginComponent,
 // 
},
{

  path: "edituser/:id",
  component: EdituserComponent,

},
{

  path: "createuser",
  component: CreateuserComponent,
  canActivate: [CreateGuard],
},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
