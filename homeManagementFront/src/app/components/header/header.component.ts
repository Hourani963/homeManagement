import { Component, OnInit } from '@angular/core';
import { IconSetService } from '@coreui/icons-angular';
import {ArticlesService} from '../../articles.service'

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit{
  value = 'Clear me';

  public allArticles = [];

  constructor(private articleService : ArticlesService){}
  ngOnInit(){
    this.allArticles =  this.articleService.getArticles();
    console.log("%c HEADER", "color:green")
    console.log(this.allArticles)
  }

}
