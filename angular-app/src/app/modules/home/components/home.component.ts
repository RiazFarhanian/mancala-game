import { Component } from '@angular/core';
import { AuthenticationService } from 'src/app/core/authentication/authentication.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  constructor(private authenticationService: AuthenticationService) {}

  isUserAuthenticated() : boolean {
    return this.authenticationService.getUsername() !== '';
  }

  authenticate() : void {
    this.authenticationService.login();
  }
}
