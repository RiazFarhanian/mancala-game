import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { MessageService } from './messageService';
import { environment } from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class StompSockService {

  private stompClients: Stomp.Client;

  constructor(private messageService: MessageService) {
    const socket = new SockJS(environment.websocketUrl);
    this.stompClients = Stomp.over(socket);
    this.stompClients.debug = () => {};
  }

  private handleReceivedMessage(message: Stomp.Message): void {
    // Publish the message to the MessageService
    this.messageService.publishMessage(message.body);
  }


  subscribe(topic: string, token: string ) :void {
    this.stompClients.connect({'Authorization':token}, (frame) => {
      this.stompClients.subscribe(topic, (message) => {
      this.handleReceivedMessage(message);
    });
  });
  }

  unsubscribe(topic: string): void {
    this.stompClients.unsubscribe(topic);
    this.stompClients.disconnect(() => {
    });
  }

}
