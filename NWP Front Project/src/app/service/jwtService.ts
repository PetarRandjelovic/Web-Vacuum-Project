// jwt.service.ts

import { Injectable } from '@angular/core';
import * as jwt from 'jwt-decode';

@Injectable({
  providedIn: 'root',
})
export class JwtService {
  /*
  decodeToken(token: string): jwt.JwtPayload | null {
    try {
      return jwt.decode(token) as jwt.JwtPayload;
    } catch (error) {
      console.error('Error decoding JWT token:', error);
      return null;
    }
  }*/
}
