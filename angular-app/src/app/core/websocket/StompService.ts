import { Injectable } from '@angular/core';
import { IStompSocket, Client, Message } from '@stomp/stompjs';

@Injectable({
  providedIn: 'root'
})
export class StompSockService {


  private client: Client;

  constructor() {
    this.client = new Client();
    this.client.webSocketFactory = () => new WebSocket('ws://localhost:3000/socket') as IStompSocket;

    this.client.onConnect = (frame) => {
      this.client.subscribe('/topic/messages', (message: Message) => {
      });
    };

    this.client.activate();
  }

  sendMessage(message: string): void {
    this.client.publish({ destination: '/send/message', body: message });
  }

  closeConnection(): void {
    this.client.deactivate();
  }
  
}
