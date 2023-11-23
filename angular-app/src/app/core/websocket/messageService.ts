import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MessageService {
  private messageSubject = new Subject<string>();

  // Observable to subscribe to for receiving messages
  message$ = this.messageSubject.asObservable();

  // Method to publish a message to subscribers
  publishMessage(message: string): void {
    this.messageSubject.next(message);
  }
}
