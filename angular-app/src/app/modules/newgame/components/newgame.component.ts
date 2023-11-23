import { Component } from '@angular/core';
import { ApiDataService } from '../../../core/http/api-data.service';
import { SessionStorageService } from 'src/app/core/store/session.storage.service';
import { AuthenticationService } from 'src/app/core/authentication/authentication.service';

@Component({
  selector: 'app-newgame',
  templateUrl: './newgame.component.html',
  styleUrls: ['./newgame.component.css']
})
export class NewgameComponent {

  constructor(private apiDataService: ApiDataService,
              private sessionStorageService: SessionStorageService,
              private authenticationService: AuthenticationService) { }

  isLoading = false;
  isGameCreated = false;

  async startGame() {
    this.isLoading = true;
    this.sessionStorageService.set('token', await this.authenticationService.getToken());
    this.authenticationService.decodeToken().then((token) => {
      this.sessionStorageService.set('user', {
        userId: token.sub,
        fullName: token.name
      });
    })
    this.apiDataService.start()
    .then(response => {
      this.sessionStorageService.set('gameId', response);
      this.isLoading = false;
      this.isGameCreated = true;
    })
  }
  
}
