import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatIconModule} from '@angular/material/icon';
import {MatDividerModule} from '@angular/material/divider';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSliderModule} from '@angular/material/slider';
import {MatInputModule} from '@angular/material/input';
import {HttpClientModule} from "@angular/common/http";
import { UserComponent } from './user/user.component';
import { LoginComponent } from './login/login.component';
import { CreateuserComponent } from './createuser/createuser.component';
import { EdituserComponent } from './edituser/edituser.component';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { WelcomeComponent } from './welcome/welcome.component';
import { VacuumSearchComponent } from './vacuum-search/vacuum-search.component';
import { CreateVacuumComponent } from './create-vacuum/create-vacuum.component';
import { GreskeComponent } from './greske/greske.component';
import { ScheduleComponent } from './schedule/schedule.component';

@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    LoginComponent,
    CreateuserComponent,
    EdituserComponent,
    WelcomeComponent,
    VacuumSearchComponent,
    CreateVacuumComponent,
    GreskeComponent,
    ScheduleComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatButtonModule,
    MatDividerModule,
   MatIconModule,
   FormsModule,
   MatToolbarModule,
   ReactiveFormsModule,
   HttpClientModule,
   MatSliderModule,
   MatInputModule,
   MatCheckboxModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
