import { BrowserModule } from '@angular/platform-browser';
import { APP_INITIALIZER, NgModule } from '@angular/core';
import {initializer} from './core/utilities/app-init';
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import { AppComponent } from './app.component';
import { AppRoutingModule } from './modules/app-routing.module';
import { HomeModule} from './modules/home/home.module';
import { NewgameModule} from './modules/newgame/newgame.module';
import { ProfileComponent } from './modules/profile/profile.component';
import { HeaderComponent } from './shared/header/header.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { FooterComponent } from './shared/footer/footer.component';
import { ScoresComponent } from './modules/scores/scores.component';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { ToastrModule } from 'ngx-toastr';

@NgModule({
  declarations: [
    AppComponent,
    ProfileComponent,
    HeaderComponent,
    FooterComponent,
    ScoresComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule, // required animations module
    ToastrModule.forRoot(), // ToastrModule added here
    AppRoutingModule,
    HomeModule,
    NewgameModule,
    KeycloakAngularModule,
    MatToolbarModule,
    MatButtonModule,
    MatTableModule,
    MatCardModule
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializer,
      deps: [ KeycloakService ],
      multi: true
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
