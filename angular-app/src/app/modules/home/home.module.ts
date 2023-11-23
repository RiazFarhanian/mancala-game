import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './components/home.component';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import {MatGridListModule} from '@angular/material/grid-list';


@NgModule({
  declarations: [HomeComponent],
  imports: [CommonModule, 
    MatCardModule,
    MatButtonModule,
    MatGridListModule
],
})
export class HomeModule {}