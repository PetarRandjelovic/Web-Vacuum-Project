import { HttpClient, HttpErrorResponse, HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { LoginRequest } from '../model';
import { Observable, catchError, throwError } from 'rxjs';
import { EventEmitter, Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';


@Injectable({
    providedIn: 'root'
  })
export class BackendService{
  decoded: any;
  public changedEventToken: EventEmitter<void>=new EventEmitter<void>();
  savedToken: any;

  updateUser(id: string, username: string, lastName: string, email: string, can_read_users: any,can_delete_users: any,can_update_users: any,can_create_users: any
    ,password:any):Observable<any>  {
    return this.httpClient.put<HttpResponse<any>>(`http://localhost:8080/api/user/editUser/${id}`, {
     
    id:id,email: email, username: username, lastName: lastName,
     can_read_users: can_read_users,can_delete_users: can_delete_users,can_update_users: can_update_users,can_create_users: can_create_users ,password:password}, {

      headers:this.headers}).pipe(
        catchError(this.handleError)
      );

    

  }
    
    private headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem("token")}`
      })

      
    constructor(private httpClient: HttpClient) { 
          this.changedEventToken.subscribe(()=>{
       

            if( localStorage!=null){
                localStorage.setItem("token",this.savedToken)
            }

            this.updateHeaders();

          });


    }
  
    createUser(username: string, lastName: string, email: string, can_read_users: any,can_delete_users: any,can_update_users: any,can_create_users: any
      ,password:string):Observable<any>  {
      return this.httpClient.post<HttpResponse<any>>(`http://localhost:8080/api/user/addUser`, {
       
      email: email, username: username, lastName: lastName,
       can_read_users: can_read_users,can_delete_users: can_delete_users,can_update_users: can_update_users,can_create_users: can_create_users ,password:password}, {
  
        headers:this.headers}).pipe(
          catchError(this.handleError)
        );
  
      
  
    }

    login(email: string, password : string):Observable<any>{



     

      return this.httpClient.post<LoginRequest>('http://localhost:8080/auth/login',{email:email, password:password}).pipe(
        catchError(this.handleError)
      );


     
      
    }

    getAllUsers():Observable<any>{
  
      console.log(this.headers)

      return this.httpClient.get('http://localhost:8080/api/user/all', {headers:this.headers})

    }

  deleteUser(id: string):Observable<any>{
   
    return this.httpClient.delete(`http://localhost:8080/api/user/del/${id}`, {headers:this.headers}).pipe(
      catchError(this.handleError)
    );

  }

  getUserById(id: number): Observable<any> {

    return this.httpClient.get(`http://localhost:8080/api/user/${id}`, {headers: this.headers}).pipe(
      catchError(this.handleError)
    );
;
  }

  private updateHeaders() {
    this.headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem('token')}`,
    });
  }
  addVacuum(name: string): Observable<any> {
    let permissionsString = localStorage.getItem("token") || '';;


    this.decoded = jwtDecode(permissionsString);
  
  
    let permissions=this.decoded.permissions;

    console.log(permissions)

    return this.httpClient.post<HttpResponse<any>>('http://localhost:8080/api/vacuum/addVacuum', {name: name}, {headers: this.headers}).pipe(
      catchError(this.handleError)
    );;
  }
  
  searchVacuum(vacuumName: string | null, status: string[] | null, dateFrom: string | null, dateTo: string | null): Observable<any>{
    return this.httpClient.put<HttpResponse<any>>('http://localhost:8080/api/vacuum/search', {vacuumName: vacuumName, status: status, dateFrom: dateFrom, dateTo: dateTo}, {headers: this.headers}).pipe(
      catchError(this.handleError)
    );
  }
/*
  searchVacuum(vacuumName: string | null, status: string[] | null, dateFrom: string | null, dateTo: string | null): Observable<any>{
    return this.httpClient.get<HttpResponse<any>>('http://localhost:8080/api/vacuum/search',{headers: this.headers} )
  }*/
  getAllVacuum(vacuumName: string | null, status: string[] | null, dateFrom: string | null, dateTo: string | null): Observable<any>{

    

    return this.httpClient.get<HttpResponse<any>>('http://localhost:8080/api/vacuum/get',{headers: this.headers} )
  }

  startVacuum(id: number): Observable<any> {
    return this.httpClient.put(`http://localhost:8080/api/vacuum/start/${id}`,{id:id} ,{headers: this.headers}).pipe(
      catchError(this.handleError)
    );
  }

  stopVacuum(id: number): Observable<any> {
    return this.httpClient.put(`http://localhost:8080/api/vacuum/stop/${id}`, {id:id} ,{headers: this.headers}).pipe(
      catchError(this.handleError)
    );
  }

  dischargeVacuum(id: number): Observable<any> {
    return this.httpClient.put(`http://localhost:8080/api/vacuum/restart/${id}`,{id:id} , {headers: this.headers}).pipe(
      catchError(this.handleError)
    );
  }
  removeVacuum(id: number): Observable<any> {
    return this.httpClient.put(`http://localhost:8080/api/vacuum/remove/${id}`, {id:id} ,{headers: this.headers}).pipe(
      catchError(this.handleError)
    );
  }
  getAllErrors(): Observable<any> {
    return this.httpClient.get(`http://localhost:8080/api/errors/get`, {headers: this.headers})
  }

  getVacuumById(id: number): Observable<any> {
    return this.httpClient.get(`http://localhost:8080/api/vacuum/get/${id}`, {headers: this.headers});
  }

  scheduleStart(id: number, date: string) {
    return this.httpClient.get(`http://localhost:8080/api/vacuum/scheduleStart/${id}?date=${date}`, {headers: this.headers}).pipe(
      catchError(this.handleError)
    );
  }

  scheduleStop(id: number, date: string) {
    return this.httpClient.get(`http://localhost:8080/api/vacuum/scheduleStop/${id}?date=${date}`, {headers: this.headers}).pipe(
      catchError(this.handleError)
    );
  }

  scheduleRestart(id: number, date: string) {
    return this.httpClient.get(`http://localhost:8080/api/vacuum/scheduleRestart/${id}?date=${date}`,{headers: this.headers}).pipe(
      catchError(this.handleError)
    );
  }
  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Unknown error occurred';
      console.log("ulazim?")
      console.log(error)
    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Server-side error
      errorMessage = `Error Code: ${error.error}\n`;
    }
    alert(errorMessage);
    // You can pass the error message to the component or log it as needed
    return throwError(errorMessage);
  }


}