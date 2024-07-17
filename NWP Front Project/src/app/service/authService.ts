import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);

  // Expose the authentication state as an observable
  isAuthenticated$: Observable<boolean> = this.isAuthenticatedSubject.asObservable();

  constructor() {
    // Check for a stored token during service initialization
    const storedToken = localStorage.getItem('token');
    this.isAuthenticatedSubject.next(!!storedToken);
  }

  // Method to update the authentication state
  setAuthenticated(isAuthenticated: boolean) {
    this.isAuthenticatedSubject.next(isAuthenticated);
  }

  // Other authentication-related methods...
}
