// Import necessary modules
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class SessionStorageService {
  constructor() {}

  // Function to set a value in sessionStorage
  set(key: string, value: any): void {
    sessionStorage.setItem(key, JSON.stringify(value));
  }

  // Function to get a value from sessionStorage
  get(key: string): any {
    const item = sessionStorage.getItem(key);
    return item ? JSON.parse(item) : null;
  }

  // Function to remove a value from sessionStorage
  remove(key: string): void {
    sessionStorage.removeItem(key);
  }
}
