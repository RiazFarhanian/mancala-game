import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './components/home.component';
import { HomeRoutingModule } from './home-routing.module';
import { BoardComponent } from '../board/board.component';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import {MatGridListModule} from '@angular/material/grid-list';


@NgModule({
  declarations: [HomeComponent, BoardComponent],
  imports: [CommonModule, 
    HomeRoutingModule,
    MatCardModule,
    MatButtonModule,
    MatGridListModule
],
})
export class HomeModule {}