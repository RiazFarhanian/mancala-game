import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/components/home.component';
import { ProfileComponent } from './profile/profile.component';
import { ScoresComponent } from './scores/scores.component';
import { NewgameComponent } from './newgame/components/newgame.component';
import { AuthenticationGuard } from '../core/authentication/authentication.guard';


const routes: Routes = [
  { path: 'home', component: HomeComponent },
  {
    path: 'newgame',
    component: NewgameComponent,
    canActivate: [AuthenticationGuard],
    // The user need to have these roles to access page
    data: {  }
  },
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [AuthenticationGuard],
    // The user need to have these roles to access page
    data: {  }
  },
  {
    path: 'scores',
    component: ScoresComponent,
    canActivate: [AuthenticationGuard],
    // The user need to have these roles to access page
    data: {  }
  },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: '**', redirectTo: '' },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

