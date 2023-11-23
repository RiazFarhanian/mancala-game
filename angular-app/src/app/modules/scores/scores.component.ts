import { Component, OnInit } from '@angular/core';
import { ApiDataService} from '../../core/http/api-data.service';

@Component({
  selector: 'app-scores',
  templateUrl: './scores.component.html',
  styleUrls: ['./scores.component.css']
})
export class ScoresComponent implements OnInit {
  
  dataSource: { date: string, yourScore: string, opponentScore: string }[] = [];

  constructor(private apiDataService: ApiDataService) {}

    ngOnInit() {
      this.apiDataService.scores().then((result) => {
        result.forEach((el: any) => {
          this.dataSource.push({
            date: el.gameDate,
            yourScore: el.playersScore[0].playerName + ': ' + el.playersScore[0].score,
            opponentScore: el.playersScore[1].playerName + ': ' + el.playersScore[1].score,
          });
        });
      });
    }

}
