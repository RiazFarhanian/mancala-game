import {Injectable} from "@angular/core";
import {KeycloakService} from "keycloak-angular";
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
    this.keycloakService.login();
  }

}
