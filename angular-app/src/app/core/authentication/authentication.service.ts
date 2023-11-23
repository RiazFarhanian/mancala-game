import {Injectable} from "@angular/core";
import {KeycloakService} from "keycloak-angular";
import * as jwtDecode from 'jwt-decode';
import { environment } from "../../../environments/environment";

@Injectable({
  providedIn: "root"
})
export class  AuthenticationService {

  constructor(private keycloakService: KeycloakService) { }

  public getUsername(): string {
    try{
      return this.keycloakService.getUsername();
    } catch(ex) {
      return '';
    }
  }

  public logout(): void {
    this.keycloakService.logout(environment.baseUrl).then(() => this.keycloakService.clearToken());
  }

  public login(): void {
    this.keycloakService.login({ redirectUri: environment.baseUrl + '/newgame' });
  }

  async getToken(): Promise<string> {
    const authenticated = await this.keycloakService.isLoggedIn();
    if (authenticated) {
      const token = await this.keycloakService.getToken();
      return token;
    } else {
      // Handle the case when the user is not authenticated
      return '';
    }
  }

  async decodeToken(): Promise<any> {
    try {
      const decodedToken = jwtDecode.jwtDecode(await this.getToken()); 
      return decodedToken;
    } catch (error) {
      console.error('Error decoding JWT token:', error);
      return null;
    }
  }
}
