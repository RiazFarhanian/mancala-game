import { Injectable } from '@angular/core';
import { AxiosInterceptorService } from './axios-interceptor.service';

@Injectable({
  providedIn: 'root',
})
export class ApiDataService {

  constructor(private axiosService: AxiosInterceptorService) { }

  start(): Promise<any> {
    return this.axiosService.post('/api/v1/waiting-room/join', {});
  }

  move(data: any): Promise<any> {
    return this.axiosService.post('/api/v1/mancala/move', data);
  }

  leave(data: any): Promise<any> {
    return this.axiosService.post('/api/v1/mancala/leave', data);
  }

  scores() : Promise<any> {
    return this.axiosService.post('/api/v1/mancala/scores', {});

  }
}
