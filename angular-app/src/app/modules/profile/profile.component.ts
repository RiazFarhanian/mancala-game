import { Component } from '@angular/core';
import { AuthenticationService } from "../../core/authentication/authentication.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent {

  username: string = '';

  constructor(private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    if(this.authenticationService.getUsername() !== undefined)
      this.username = this.authenticationService.getUsername();
  }

}
