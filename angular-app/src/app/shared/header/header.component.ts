import { Component } from '@angular/core';
import { AuthenticationService } from 'src/app/core/authentication/authentication.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  constructor(private authenticationService: AuthenticationService) {
  }

  public logout() {
    this.authenticationService.logout();
  }

  public login(){
    
  }
}
